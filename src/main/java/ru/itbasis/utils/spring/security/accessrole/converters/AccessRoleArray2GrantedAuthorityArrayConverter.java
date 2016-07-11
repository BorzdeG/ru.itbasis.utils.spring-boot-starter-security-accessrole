package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;
import ru.itbasis.utils.spring.security.accessrole.comparators.GrantedAuthorityComparator;

import java.util.Arrays;
import java.util.stream.Stream;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Slf4j
@AccessRoleConverter
public final class AccessRoleArray2GrantedAuthorityArrayConverter implements Converter<IAccessRole[], GrantedAuthority[]> {
	private final AccessRole2GrantedAuthorityConverter accessRole2GrantedAuthorityConverter;
	private final GrantedAuthorityComparator           grantedAuthorityComparator;

	@Autowired
	public AccessRoleArray2GrantedAuthorityArrayConverter(final AccessRole2GrantedAuthorityConverter accessRole2GrantedAuthorityConverter,
																												final GrantedAuthorityComparator grantedAuthorityComparator) {
		this.accessRole2GrantedAuthorityConverter = accessRole2GrantedAuthorityConverter;
		this.grantedAuthorityComparator = grantedAuthorityComparator;
	}

	@Override
	public GrantedAuthority[] convert(final IAccessRole[] source) {
		if (log.isDebugEnabled()) { log.debug("source: {}", Arrays.asList(source)); }

		if (ArrayUtils.isEmpty(source)) { return new GrantedAuthority[]{}; }

		final GrantedAuthority[] target = Stream.of(source)
																						.map(accessRole2GrantedAuthorityConverter::convert)
																						.sorted(grantedAuthorityComparator)
																						.distinct()
																						.toArray(GrantedAuthority[]::new);
		if (log.isDebugEnabled()) { log.debug("target: {}", Arrays.asList(target)); }
		return target;
	}

}
