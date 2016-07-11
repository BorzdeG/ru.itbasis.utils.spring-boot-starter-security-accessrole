package ru.itbasis.utils.spring.security.accessrole.comparators;

import org.springframework.stereotype.Component;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;

import java.util.Comparator;

@Component
public class AccessRoleComparator implements Comparator<IAccessRole> {

	@Override
	public int compare(final IAccessRole left, final IAccessRole right) {
		if (left == null || right == null) { return 0; }

		return left.getFullName().compareTo(right.getFullName());
	}
}
