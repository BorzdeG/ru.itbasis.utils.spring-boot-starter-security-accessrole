package sample.ru.itbasis.utils.spring.security.accessrole;

import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRole;

@AccessRole
public enum CoreAccessRole implements IAccessRole {
	GUEST,
	ADMIN;

	@Override
	public String getPrefix() { return "CORE_";}
}
