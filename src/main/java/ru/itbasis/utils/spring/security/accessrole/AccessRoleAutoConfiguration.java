package ru.itbasis.utils.spring.security.accessrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.security.core.GrantedAuthority;
import ru.itbasis.utils.spring.security.accessrole.comparators.AccessRoleComparator;
import ru.itbasis.utils.spring.security.accessrole.comparators.GrantedAuthorityComparator;
import ru.itbasis.utils.spring.security.accessrole.converters.*;
import ru.itbasis.utils.spring.security.accessrole.factory.AccessRoleConversionServiceFactory;
import ru.itbasis.utils.spring.security.accessrole.processor.AccessRoleRegistryPostProcessor;

@Configuration
@ConditionalOnClass({GrantedAuthority.class})
@AutoConfigureBefore({SecurityAutoConfiguration.class})
public class AccessRoleAutoConfiguration {
	@Bean
	public AccessRoleConversionServiceFactory accessRoleConversionServiceFactory(final ConversionService conversionService) {
		return new AccessRoleConversionServiceFactory(conversionService);
	}

	@Bean
	public AccessRoleRegistryPostProcessor accessRoleRegistryPostProcessor() {
		return new AccessRoleRegistryPostProcessor();
	}

	@Configuration
	public class AccessRoleComparatorsConfiguration {
		@Bean
		public AccessRoleComparator accessRoleComparator() {
			return new AccessRoleComparator();
		}

		@Bean
		public GrantedAuthorityComparator grantedAuthorityComparator() {
			return new GrantedAuthorityComparator();
		}
	}

	@Configuration
	@ConditionalOnMissingBean(ConversionService.class)
	protected class AccessRoleConversionServiceConfiguration {
		@Bean
		public ConversionService conversionService() {
			return new DefaultConversionService();
		}
	}

	@Configuration
	public class AccessRoleConvertersConfiguration {
		private final AccessRoleComparator       accessRoleComparator;
		private final GrantedAuthorityComparator grantedAuthorityComparator;

		@Autowired
		public AccessRoleConvertersConfiguration(
			final AccessRoleComparator accessRoleComparator,
			final GrantedAuthorityComparator grantedAuthorityComparator
		) {
			this.accessRoleComparator = accessRoleComparator;
			this.grantedAuthorityComparator = grantedAuthorityComparator;
		}

		@Bean
		public AccessRole2GrantedAuthorityConverter accessRole2GrantedAuthorityConverter(final AccessRole2StringConverter accessRole2StringConverter) {
			return new AccessRole2GrantedAuthorityConverter(accessRole2StringConverter);
		}

		@Bean
		public AccessRole2StringConverter accessRole2StringConverter() {
			return new AccessRole2StringConverter();
		}

		@Bean
		public AccessRoleArray2GrantedAuthorityArrayConverter accessRoleArray2GrantedAuthorityArrayConverter(
			final AccessRole2GrantedAuthorityConverter accessRole2GrantedAuthorityConverter
		) {
			return new AccessRoleArray2GrantedAuthorityArrayConverter(accessRole2GrantedAuthorityConverter, grantedAuthorityComparator);
		}

		@Bean
		public AccessRoleArray2StringArrayConverter accessRoleArray2StringArrayConverter(final AccessRole2StringConverter accessRole2StringConverter) {
			return new AccessRoleArray2StringArrayConverter(accessRole2StringConverter);
		}

		@Bean
		public AccessRoleArray2StringConverter accessRoleArray2StringConverter(final AccessRole2StringConverter accessRole2StringConverter) {
			return new AccessRoleArray2StringConverter(accessRole2StringConverter);
		}

		@Bean
		public GrantedAuthority2AccessRoleConverter grantedAuthority2AccessRoleConverter(final String2AccessRoleConverter string2AccessRoleConverter) {
			return new GrantedAuthority2AccessRoleConverter(string2AccessRoleConverter);
		}

		@Bean
		public GrantedAuthorityArray2AccessRoleArrayConverter grantedAuthorityArray2AccessRoleArrayConverter(
			final GrantedAuthority2AccessRoleConverter grantedAuthority2AccessRoleConverter
		) {
			return new GrantedAuthorityArray2AccessRoleArrayConverter(grantedAuthority2AccessRoleConverter, accessRoleComparator);
		}

		@Bean
		public String2AccessRoleArrayConverter string2AccessRoleArrayConverter(final String2AccessRoleConverter string2AccessRoleConverter) {
			return new String2AccessRoleArrayConverter(string2AccessRoleConverter, accessRoleComparator);
		}

		@Bean
		public String2AccessRoleConverter string2AccessRoleConverter() {
			return new String2AccessRoleConverter();
		}
	}
}
