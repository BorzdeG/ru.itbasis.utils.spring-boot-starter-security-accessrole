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

public class GrantedAuthorityArray2AccessRoleArrayConverterTest extends AbstractCoreTests {
	@Autowired
	private GrantedAuthorityArray2AccessRoleArrayConverter converter;

	@DataProvider(name = "dataConvert")
	public static Object[][] dataConvert() {
		return new Object[][]{
			{new GrantedAuthority[]{GRANTED_AUTHORITY_CORE_GUEST, GRANTED_AUTHORITY_CORE_ADMIN},
			 new IAccessRole[]{CoreAccessRole.ADMIN, CoreAccessRole.GUEST}}
			,
			{new GrantedAuthority[]{GRANTED_AUTHORITY_CORE_ADMIN, GRANTED_AUTHORITY_CORE_GUEST},
			 new IAccessRole[]{CoreAccessRole.ADMIN, CoreAccessRole.GUEST}}
			,
			{new GrantedAuthority[]{GRANTED_AUTHORITY_CORE_ADMIN, GRANTED_AUTHORITY_CORE_ADMIN}, new IAccessRole[]{CoreAccessRole.ADMIN}}
			,
			{new GrantedAuthority[]{GRANTED_AUTHORITY_CUSTOM_GUEST, GRANTED_AUTHORITY_CORE_GUEST},
			 new IAccessRole[]{CoreAccessRole.GUEST, CustomAccessRole.GUEST}
			}
		};
	}

	@Test(dataProvider = "dataConvert")
	public void testConvert(final GrantedAuthority[] authorities, final IAccessRole[] expectedRoles) throws Exception {
		Assert.assertEquals(converter.convert(authorities), expectedRoles);
		Assert.assertEquals(conversionService.convert(authorities, IAccessRole[].class), expectedRoles);
	}
}
