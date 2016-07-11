package ru.itbasis.utils.spring.security.accessrole;

import java.io.Serializable;

public interface IAccessRole extends Serializable {
	String AUTHORITY_PREFIX = "ROLE_";

	String BEAN_PREFIX = "ACCESS_ROLE_";

	default String getFullName() { return getPrefix() + name();}

	String getPrefix();

	String name();
}
