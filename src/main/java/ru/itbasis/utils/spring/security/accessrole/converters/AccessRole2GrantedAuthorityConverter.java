package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;

import static org.apache.commons.lang3.StringUtils.prependIfMissingIgnoreCase;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Slf4j
@AccessRoleConverter
public final class AccessRole2GrantedAuthorityConverter implements Converter<IAccessRole, GrantedAuthority> {
	private final AccessRole2StringConverter accessRole2StringConverter;

	@Autowired
	public AccessRole2GrantedAuthorityConverter(final AccessRole2StringConverter accessRole2StringConverter) {
		this.accessRole2StringConverter = accessRole2StringConverter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GrantedAuthority convert(final IAccessRole source) {
		log.trace("source: {}", source);

		final String roleName = accessRole2StringConverter.convert(source);
		log.trace("roleName: {}", roleName);
		final GrantedAuthority target = new SimpleGrantedAuthority(prependIfMissingIgnoreCase(roleName, IAccessRole.AUTHORITY_PREFIX));
		log.trace("target: {}", target);
		return target;
	}

}
