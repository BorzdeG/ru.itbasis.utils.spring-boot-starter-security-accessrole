package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;
import ru.itbasis.utils.spring.security.accessrole.comparators.AccessRoleComparator;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;

@Slf4j
@AccessRoleConverter
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public class GrantedAuthorityArray2AccessRoleArrayConverter implements Converter<GrantedAuthority[], IAccessRole[]> {
	private final GrantedAuthority2AccessRoleConverter converter;
	private final AccessRoleComparator                 comparator;

	public GrantedAuthorityArray2AccessRoleArrayConverter(final GrantedAuthority2AccessRoleConverter grantedAuthority2AccessRoleConverter,
																												final AccessRoleComparator accessRoleComparator) {
		this.converter = grantedAuthority2AccessRoleConverter;
		this.comparator = accessRoleComparator;
	}

	@Override
	public IAccessRole[] convert(final GrantedAuthority[] source) {
		if (log.isDebugEnabled()) { log.debug("source: ", Arrays.asList(source)); }

		if (isEmpty(source)) { return new IAccessRole[]{}; }

		final IAccessRole[] target = Stream.of(source)
																			 .map(converter::convert)
																			 .sorted(comparator)
																			 .distinct()
																			 .toArray(IAccessRole[]::new);
		if (log.isDebugEnabled()) { log.debug("target: {}", Arrays.asList(target)); }
		return target;
	}

}
