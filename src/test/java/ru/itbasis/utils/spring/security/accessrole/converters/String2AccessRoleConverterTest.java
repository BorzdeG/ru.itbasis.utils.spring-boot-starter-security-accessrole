package ru.itbasis.utils.spring.security.accessrole.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.AbstractCoreTests;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CoreAccessRole;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class String2AccessRoleConverterTest extends AbstractCoreTests {
	@Autowired
	private String2AccessRoleConverter converter;

	@DataProvider(name = "dataConvert")
	public static Object[][] dataConvert() {
		return new Object[][]{
			{null, null}
			, {EMPTY, null}
			, {SPACE, null}
			, {"CORE_GUEST", CoreAccessRole.GUEST}
			, {" CORE_GUEST", CoreAccessRole.GUEST}
			, {"CORE_GUEST ", CoreAccessRole.GUEST}
			, {"CORE_ADMIN", CoreAccessRole.ADMIN}
		};
	}

	@Test(dataProvider = "dataConvert")
	public void testConvert(final String roleName, final IAccessRole expectedRole) throws Exception {
		Assert.assertEquals(converter.convert(roleName), expectedRole);
		Assert.assertEquals(conversionService.convert(roleName, IAccessRole.class), expectedRole);
	}
}
