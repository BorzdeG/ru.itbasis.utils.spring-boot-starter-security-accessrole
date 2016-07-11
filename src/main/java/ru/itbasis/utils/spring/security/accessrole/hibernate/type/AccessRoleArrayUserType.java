package ru.itbasis.utils.spring.security.accessrole.hibernate.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.comparators.AccessRoleComparator;
import ru.itbasis.utils.spring.security.accessrole.converters.AccessRoleArray2StringConverter;
import ru.itbasis.utils.spring.security.accessrole.converters.String2AccessRoleArrayConverter;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

public class AccessRoleArrayUserType implements UserType, ApplicationContextAware {

	private static AccessRoleComparator            accessRoleComparator;
	private static AccessRoleArray2StringConverter accessRoleArray2StringConverter;
	private static String2AccessRoleArrayConverter string2AccessRoleArrayConverter;

	@Override
	public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(final Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public boolean equals(final Object left, final Object right) throws HibernateException {
		return (left == right) ||
					 (left instanceof IAccessRole[] && right instanceof IAccessRole[] && Arrays.equals((IAccessRole[]) left, (IAccessRole[]) right));
	}

	@Override
	public int hashCode(final Object x) throws HibernateException {
		assert x != null;
		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session, final Object owner) throws HibernateException, SQLException {
		final String value = rs.getString(names[0]);
		if (value == null || value.trim().isEmpty()) { return null; }

		final IAccessRole[] convert = string2AccessRoleArrayConverter.convert(value);
		if (convert.length < 1) { return null; }

		return convert;
	}

	@Override
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session) throws HibernateException, SQLException {
		assert accessRoleArray2StringConverter != null;

		if (value == null) {
			st.setString(index, null);
			return;
		}

		final String convert = accessRoleArray2StringConverter.convert((IAccessRole[]) value);
		if (convert == null || convert.trim().isEmpty()) {
			st.setString(index, null);
			return;
		}

		st.setString(index, convert);
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}

	@Override
	public Class returnedClass() {
		return IAccessRole[].class;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		accessRoleComparator = applicationContext.getBean(AccessRoleComparator.class);
		accessRoleArray2StringConverter = applicationContext.getBean(AccessRoleArray2StringConverter.class);
		string2AccessRoleArrayConverter = applicationContext.getBean(String2AccessRoleArrayConverter.class);
	}

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.VARCHAR};
	}
}
