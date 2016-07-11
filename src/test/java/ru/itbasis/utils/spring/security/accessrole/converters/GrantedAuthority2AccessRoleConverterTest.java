package ru.itbasis.utils.spring.security.accessrole.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.AbstractCoreTests;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CoreAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CustomAccessRole;

public class GrantedAuthority2AccessRoleConverterTest extends AbstractCoreTests {
	@Autowired
	private GrantedAuthority2AccessRoleConverter converter;

	@DataProvider(name = "dataConvert")
	public static Object[][] dataConvert() {
		return new Object[][]{
			{GRANTED_AUTHORITY_CORE_GUEST, CoreAccessRole.GUEST}
			, {GRANTED_AUTHORITY_CORE_ADMIN, CoreAccessRole.ADMIN}
			, {GRANTED_AUTHORITY_CUSTOM_GUEST, CustomAccessRole.GUEST}
		};
	}

	@Test(dataProvider = "dataConvert")
	public void testConvert(final GrantedAuthority authority, final IAccessRole expectedRole) throws Exception {
		Assert.assertEquals(converter.convert(authority), expectedRole);
		Assert.assertEquals(conversionService.convert(authority, IAccessRole.class), expectedRole);
	}
}
