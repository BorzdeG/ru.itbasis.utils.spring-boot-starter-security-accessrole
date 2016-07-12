package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@AccessRoleConverter
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public final class AccessRoleArray2StringConverter implements Converter<IAccessRole[], String> {
	private final AccessRole2StringConverter accessRole2StringConverter;

	@Autowired
	public AccessRoleArray2StringConverter(final AccessRole2StringConverter accessRole2StringConverter) {
		this.accessRole2StringConverter = accessRole2StringConverter;
	}

	@Override
	public String convert(final IAccessRole[] source) {
		if (log.isDebugEnabled()) { log.debug("source: {}", Arrays.asList(source)); }

		if (ArrayUtils.isEmpty(source)) { return EMPTY; }

		final List<String> strings = Stream.of(source)
																			 .filter(accessRole -> accessRole != null)
																			 .map(accessRole2StringConverter::convert)
																			 .sorted(String::compareTo)
																			 .distinct()
																			 .collect(Collectors.toList());
		final String target = StringUtils.collectionToCommaDelimitedString(strings);
		log.debug("target: {}", target);
		return target;
	}
}
