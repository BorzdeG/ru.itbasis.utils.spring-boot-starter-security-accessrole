package ru.itbasis.utils.spring.security.accessrole.hibernate.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.converters.AccessRole2StringConverter;
import ru.itbasis.utils.spring.security.accessrole.converters.String2AccessRoleConverter;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class AccessRoleUserType implements UserType, ApplicationContextAware {

	//	private static AccessRoleComparator       accessRoleComparator;
	private static AccessRole2StringConverter accessRole2StringConverter;
	private static String2AccessRoleConverter string2AccessRoleConverter;

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
		return left == right ||
					 (left instanceof IAccessRole && right instanceof IAccessRole && left.equals(right));
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
		assert string2AccessRoleConverter != null;

		final String string = rs.getString(names[0]);
		if (string == null || string.trim().isEmpty()) { return null; }

		return string2AccessRoleConverter.convert(string);
	}

	@Override
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session) throws HibernateException, SQLException {
		assert accessRole2StringConverter != null;

		if (value == null) {
			st.setString(index, null);
			return;
		}

		st.setString(index, accessRole2StringConverter.convert((IAccessRole) value));
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}

	@Override
	public Class returnedClass() {
		return IAccessRole.class;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
//		accessRoleComparator = applicationContext.getBean(AccessRoleComparator.class);
		accessRole2StringConverter = applicationContext.getBean(AccessRole2StringConverter.class);
		string2AccessRoleConverter = applicationContext.getBean(String2AccessRoleConverter.class);
	}

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.VARCHAR};
	}
}
