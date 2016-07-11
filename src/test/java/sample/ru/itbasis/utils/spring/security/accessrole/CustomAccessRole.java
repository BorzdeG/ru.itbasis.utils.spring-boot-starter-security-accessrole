package sample.ru.itbasis.utils.spring.security.accessrole;

import ru.itbasis.utils.spring.security.accessrole.IAccessRole;

@sample.ru.itbasis.utils.spring.security.accessrole.annotation.CustomAccessRole
public enum CustomAccessRole implements IAccessRole {
	GUEST;

	@Override
	public String getPrefix() {
		return "CUSTOM_";
	}
}
