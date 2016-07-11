package ru.itbasis.utils.spring.security.accessrole;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import ru.itbasis.utils.spring.security.accessrole.hibernate.type.AccessRoleArrayUserType;
import ru.itbasis.utils.spring.security.accessrole.hibernate.type.AccessRoleUserType;

@Configuration
@ImportAutoConfiguration(AccessRoleAutoConfiguration.class)
@AutoConfigureAfter({HibernateJpaAutoConfiguration.class, AccessRoleAutoConfiguration.class})
public class AccessRoleJpaAutoConfiguration {
	@Order(Ordered.HIGHEST_PRECEDENCE + 20)
	private static class HibernateEntityManagerConditional extends SpringBootCondition {
		private static String[] CLASS_NAMES = {
			"org.hibernate.ejb.HibernateEntityManager",
			"org.hibernate.jpa.HibernateEntityManager"};

		@Override
		public ConditionOutcome getMatchOutcome(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
			for (String className : CLASS_NAMES) {
				if (ClassUtils.isPresent(className, context.getClassLoader())) {
					return ConditionOutcome.match("found HibernateEntityManager class");
				}
			}
			return ConditionOutcome.noMatch("did not find HibernateEntityManager class");
		}
	}

	@Configuration
	@Conditional(HibernateEntityManagerConditional.class)
	public class AccessRoleHibernateConfiguration {
		@Bean
		public AccessRoleArrayUserType accessRoleArrayUserType() {
			return new AccessRoleArrayUserType();
		}

		@Bean
		public AccessRoleUserType accessRoleUserType() {
			return new AccessRoleUserType();
		}
	}
}
