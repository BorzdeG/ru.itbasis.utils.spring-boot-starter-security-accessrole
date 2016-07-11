package ru.itbasis.utils.spring.security.accessrole.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.AbstractCoreTests;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CoreAccessRole;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class String2AccessRoleArrayConverterTest extends AbstractCoreTests {
	@Autowired
	protected String2AccessRoleArrayConverter converter;

	@DataProvider(name = "dataConvert")
	public static Object[][] dataConvert() {
		return new Object[][]{
			{EMPTY, new IAccessRole[]{}}
			, {"CORE_GUEST", new IAccessRole[]{CoreAccessRole.GUEST}}
			, {"CORE_ADMIN,CORE_GUEST", new IAccessRole[]{CoreAccessRole.ADMIN, CoreAccessRole.GUEST}}
			, {"CORE_GUEST,CORE_ADMIN", new IAccessRole[]{CoreAccessRole.ADMIN, CoreAccessRole.GUEST}}
		};
	}

	@Test(dataProvider = "dataConvert")
	public void testConvert(final String source, final IAccessRole[] expectedRoles) throws Exception {
		Assert.assertEquals(converter.convert(source), expectedRoles);
		Assert.assertEquals(conversionService.convert(source, IAccessRole[].class), expectedRoles);
	}
}
