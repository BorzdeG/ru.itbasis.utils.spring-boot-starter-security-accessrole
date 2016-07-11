package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;

import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
@AccessRoleConverter
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public final class AccessRoleArray2StringArrayConverter implements Converter<IAccessRole[], String[]> {
	private AccessRole2StringConverter accessRole2StringConverter;

	@Autowired
	public AccessRoleArray2StringArrayConverter(final AccessRole2StringConverter accessRole2StringConverter) {
		this.accessRole2StringConverter = accessRole2StringConverter;
	}

	@Override
	public String[] convert(final IAccessRole[] source) {
		if (log.isDebugEnabled()) { log.debug("source: {}", Arrays.asList(source)); }

		if (ArrayUtils.isEmpty(source)) { return new String[]{}; }

		final String[] target = Stream.of(source)
																	.map(accessRole2StringConverter::convert)
																	.sorted(String::compareTo)
																	.distinct()
																	.toArray(String[]::new);

		if (log.isDebugEnabled()) { log.debug("target: {}", Arrays.asList(target)); }
		return target;
	}
}
