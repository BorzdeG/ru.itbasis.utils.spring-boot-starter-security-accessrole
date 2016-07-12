package ru.itbasis.utils.spring.security.accessrole.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.AbstractCoreTests;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CoreAccessRole;

public class AccessRole2StringConverterTest extends AbstractCoreTests {
	@Autowired
	private AccessRole2StringConverter accessRole2StringConverter;

	@DataProvider(name = "dataConvert")
	public static Object[][] dataConvert() {
		return new Object[][]{
			{null, null}
			, {CoreAccessRole.GUEST, "CORE_GUEST"}
			, {CoreAccessRole.ADMIN, "CORE_ADMIN"}
		};
	}

	@Test(dataProvider = "dataConvert")
	public void testConvert(final IAccessRole role, final String expectedRoleName) throws Exception {
		Assert.assertEquals(accessRole2StringConverter.convert(role), expectedRoleName);
		Assert.assertEquals(conversionService.convert(role, String.class), expectedRoleName);
	}
}
