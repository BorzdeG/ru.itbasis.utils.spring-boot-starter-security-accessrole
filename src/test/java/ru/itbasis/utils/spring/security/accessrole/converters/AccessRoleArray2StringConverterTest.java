package ru.itbasis.utils.spring.security.accessrole.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.AbstractCoreTests;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CoreAccessRole;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class AccessRoleArray2StringConverterTest extends AbstractCoreTests {
	@Autowired
	private AccessRoleArray2StringConverter converter;

	@DataProvider(name = "dataConvert")
	public static Object[][] dataConvert() {
		return new Object[][]{
			{new IAccessRole[]{}, EMPTY}
			, {new IAccessRole[]{null}, EMPTY}
			, {new IAccessRole[]{CoreAccessRole.GUEST}, "CORE_GUEST"}
			, {new IAccessRole[]{CoreAccessRole.GUEST, CoreAccessRole.ADMIN}, "CORE_ADMIN,CORE_GUEST"}
			, {new IAccessRole[]{CoreAccessRole.ADMIN, CoreAccessRole.GUEST}, "CORE_ADMIN,CORE_GUEST"}
		};
	}

	@Test(dataProvider = "dataConvert")
	public void testConvert(final IAccessRole[] roles, final String expected) throws Exception {
		Assert.assertEquals(converter.convert(roles), expected);
		Assert.assertEquals(conversionService.convert(roles, String.class), expected);
	}
}
