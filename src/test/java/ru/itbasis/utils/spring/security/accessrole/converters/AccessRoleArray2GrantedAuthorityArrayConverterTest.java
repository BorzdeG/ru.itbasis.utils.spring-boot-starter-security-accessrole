package ru.itbasis.utils.spring.security.accessrole.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.AbstractCoreTests;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CoreAccessRole;

public class AccessRoleArray2GrantedAuthorityArrayConverterTest extends AbstractCoreTests {
	@Autowired
	private AccessRoleArray2GrantedAuthorityArrayConverter converter;

	@DataProvider(name = "dataConvert")
	public static Object[][] dataConvert() {
		return new Object[][]{
			{new IAccessRole[]{}, new GrantedAuthority[]{}}
			,
			{new IAccessRole[]{CoreAccessRole.GUEST}, new GrantedAuthority[]{GRANTED_AUTHORITY_CORE_GUEST}}
			,
			{new IAccessRole[]{CoreAccessRole.GUEST, CoreAccessRole.ADMIN},
			 new GrantedAuthority[]{GRANTED_AUTHORITY_CORE_ADMIN, GRANTED_AUTHORITY_CORE_GUEST}}
			,
			{new IAccessRole[]{CoreAccessRole.ADMIN, CoreAccessRole.GUEST},
			 new GrantedAuthority[]{GRANTED_AUTHORITY_CORE_ADMIN, GRANTED_AUTHORITY_CORE_GUEST}}
		};
	}

	@Test(dataProvider = "dataConvert")
	public void testConvert(final IAccessRole[] roles, final GrantedAuthority[] expectedAuthorities) throws Exception {
		Assert.assertEquals(converter.convert(roles), expectedAuthorities);
		Assert.assertEquals(conversionService.convert(roles, GrantedAuthority[].class), expectedAuthorities);
	}
}
