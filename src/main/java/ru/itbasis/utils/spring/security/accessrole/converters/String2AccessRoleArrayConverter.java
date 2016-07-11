package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;
import ru.itbasis.utils.spring.security.accessrole.comparators.AccessRoleComparator;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.springframework.util.StringUtils.commaDelimitedListToStringArray;

@Slf4j
@AccessRoleConverter
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public final class String2AccessRoleArrayConverter implements Converter<String, IAccessRole[]> {
	private final String2AccessRoleConverter string2AccessRoleConverter;
	private final AccessRoleComparator       accessRoleComparator;

	public String2AccessRoleArrayConverter(final String2AccessRoleConverter string2AccessRoleConverter,
																				 final AccessRoleComparator accessRoleComparator) {
		this.string2AccessRoleConverter = string2AccessRoleConverter;
		this.accessRoleComparator = accessRoleComparator;
	}

	@Override
	public IAccessRole[] convert(final String source) {
		log.debug("source: {}", source);

		if (trimToEmpty(source).isEmpty()) { return new IAccessRole[]{}; }

		final IAccessRole[] target = Stream.of(commaDelimitedListToStringArray(source))
																			 .parallel()
																			 .map(string2AccessRoleConverter::convert)
																			 .sorted(accessRoleComparator)
																			 .toArray(IAccessRole[]::new);
		if (log.isDebugEnabled()) { log.debug("target: {}", Arrays.asList(target)); }
		return target;
	}
}
