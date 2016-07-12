package ru.itbasis.utils.spring.security.accessrole.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.AbstractCoreTests;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CoreAccessRole;

public class AccessRole2GrantedAuthorityConverterTest extends AbstractCoreTests {
	@Autowired
	private AccessRole2GrantedAuthorityConverter converter;

	@DataProvider(name = "dataConvert")
	public static Object[][] dataConvert() {
		return new Object[][]{
			{CoreAccessRole.GUEST, new SimpleGrantedAuthority(IAccessRole.AUTHORITY_PREFIX + "CORE_GUEST")}
			, {CoreAccessRole.ADMIN, new SimpleGrantedAuthority(IAccessRole.AUTHORITY_PREFIX + "CORE_ADMIN")}
		};
	}

	@Test(dataProvider = "dataConvert")
	public void testConvert(final IAccessRole role, final GrantedAuthority expectedGrantedAuthority) throws Exception {
		Assert.assertEquals(converter.convert(role), expectedGrantedAuthority);
		Assert.assertEquals(conversionService.convert(role, GrantedAuthority.class), expectedGrantedAuthority);
	}
}