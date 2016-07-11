package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;

@Slf4j
@AccessRoleConverter
public final class AccessRole2StringConverter implements Converter<IAccessRole, String> {

	@Override
	@SuppressWarnings("unchecked")
	public String convert(final IAccessRole source) {
		log.debug("source: {}", source);

		final String target = source.getFullName();
		log.debug("target: {}", target);
		return target;
	}
}
