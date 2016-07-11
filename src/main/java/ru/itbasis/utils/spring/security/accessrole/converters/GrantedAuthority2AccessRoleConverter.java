package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;

import static org.apache.commons.lang3.StringUtils.removeStartIgnoreCase;

@Slf4j
@AccessRoleConverter
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public final class GrantedAuthority2AccessRoleConverter implements Converter<GrantedAuthority, IAccessRole>, InitializingBean {
	private final String2AccessRoleConverter string2AccessRoleConverter;

	@Autowired
	public GrantedAuthority2AccessRoleConverter(final String2AccessRoleConverter string2AccessRoleConverter) {
		this.string2AccessRoleConverter = string2AccessRoleConverter;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("string2AccessRoleConverter: {}", string2AccessRoleConverter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAccessRole convert(final GrantedAuthority source) {
		log.trace("source: {}", source);

		final String roleName = removeStartIgnoreCase(source.getAuthority(), IAccessRole.AUTHORITY_PREFIX);
		log.trace("roleName: {}", roleName);
		final IAccessRole target = string2AccessRoleConverter.convert(roleName);
		log.trace("target: {}", target);
		return target;
	}

}
