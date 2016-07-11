package ru.itbasis.utils.spring.security.accessrole.comparators;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class GrantedAuthorityComparator implements Comparator<GrantedAuthority> {

	@Override
	public int compare(final GrantedAuthority left, final GrantedAuthority right) {
		if (left == null || right == null) { return 0; }
		return left.getAuthority().compareTo(right.getAuthority());
	}
}
