package ru.itbasis.utils.spring.security.accessrole.hibernate.integrator;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.hibernate.usertype.UserType;
import ru.itbasis.utils.spring.security.accessrole.hibernate.type.AccessRoleArrayUserType;
import ru.itbasis.utils.spring.security.accessrole.hibernate.type.AccessRoleUserType;

public class AccessRoleHibernateIntegrator implements Integrator {
	@Override
	public void disintegrate(final SessionFactoryImplementor sessionFactory, final SessionFactoryServiceRegistry serviceRegistry) { }

	@Override
	public void integrate(final Metadata metadata, final SessionFactoryImplementor sessionFactory, final SessionFactoryServiceRegistry serviceRegistry) {
		final MetadataImplementor metadataImplementor = (MetadataImplementor) metadata;

		registerType(metadataImplementor, new AccessRoleUserType());
		registerType(metadataImplementor, new AccessRoleArrayUserType());
	}

	private void registerType(final MetadataImplementor metadataImplementor, final UserType userType) {
		metadataImplementor.getTypeResolver()
											 .registerTypeOverride(userType,
																						 new String[]{userType.returnedClass().getName()}
											 );
	}
}
