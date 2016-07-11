package ru.itbasis.utils.spring.security.accessrole.hibernate.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.CoreAccessRole;
import sample.ru.itbasis.utils.spring.security.accessrole.TestBootApplication;
import sample.ru.itbasis.utils.spring.security.accessrole.entity.TestEntity;
import sample.ru.itbasis.utils.spring.security.accessrole.repository.TestEntityRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(classes = {TestBootApplication.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AccessRoleJavaTypeDescriptorTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private JdbcTemplate         jdbcTemplate;
	@Autowired
	private TestEntityRepository testEntityRepository;

	@DataProvider(name = "dataToStringArray")
	public static Object[][] dataToStringArray() {
		return new Object[][]{
			{new IAccessRole[]{CoreAccessRole.GUEST}, "CORE_GUEST"}
			, {new IAccessRole[]{CoreAccessRole.GUEST, CoreAccessRole.ADMIN}, "CORE_ADMIN,CORE_GUEST"}
			, {new IAccessRole[]{CoreAccessRole.ADMIN, CoreAccessRole.GUEST}, "CORE_ADMIN,CORE_GUEST"}
			, {new IAccessRole[]{CoreAccessRole.GUEST, CoreAccessRole.GUEST}, "CORE_GUEST"}
			, {new IAccessRole[]{CoreAccessRole.ADMIN}, "CORE_ADMIN"}
		};
	}

	@DataProvider(name = "dataToStringArrayAsNull")
	public static Object[][] dataToStringArrayAsNull() {
		return new Object[][]{
			{null}
			, {new IAccessRole[]{}}
		};
	}

	@DataProvider(name = "dataToStringElementCollectionList")
	public static Object[][] dataToStringElementCollectionList() {
		return new Object[][]{
			{Collections.singletonList(CoreAccessRole.ADMIN)}
			, {Collections.singletonList(CoreAccessRole.GUEST)}
			, {Arrays.asList(CoreAccessRole.GUEST, CoreAccessRole.ADMIN)}
			, {Arrays.asList(CoreAccessRole.ADMIN, CoreAccessRole.GUEST)}
			, {Arrays.asList(CoreAccessRole.ADMIN, CoreAccessRole.ADMIN)}
			, {Arrays.asList(CoreAccessRole.ADMIN, CoreAccessRole.ADMIN, CoreAccessRole.GUEST)}
		};
	}

	@DataProvider(name = "dataToStringElementCollectionSet")
	public static Object[][] dataToStringElementCollectionSet() {
		return new Object[][]{
			{Collections.singletonList(CoreAccessRole.ADMIN), 1}
			, {Collections.singletonList(CoreAccessRole.GUEST), 1}
			, {Arrays.asList(CoreAccessRole.GUEST, CoreAccessRole.ADMIN), 2}
			, {Arrays.asList(CoreAccessRole.ADMIN, CoreAccessRole.GUEST), 2}
			, {Arrays.asList(CoreAccessRole.ADMIN, CoreAccessRole.ADMIN), 1}
			, {Arrays.asList(CoreAccessRole.ADMIN, CoreAccessRole.ADMIN, CoreAccessRole.GUEST), 2}
		};
	}

	@DataProvider(name = "dataToStringOne")
	public static Object[][] dataToStringOne() {
		return new Object[][]{
			{CoreAccessRole.GUEST, "CORE_GUEST"}
			, {CoreAccessRole.ADMIN, "CORE_ADMIN"}
		};
	}

	@Test(dataProvider = "dataToStringArray")
	public void testToStringArray(final IAccessRole[] accessRoles, final String expectedAccessRoles) throws Exception {
		final TestEntity testEntity = new TestEntity();
		testEntity.setRoles(accessRoles);

		final TestEntity savedTestEntity = testEntityRepository.save(testEntity);
		Assert.assertEquals(savedTestEntity.getRole(), testEntity.getRole());

		final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from test_entity where id=?", savedTestEntity.getId());
		Assert.assertTrue(sqlRowSet.next());
		final String role = sqlRowSet.getString("roles");
		Assert.assertEquals(role, expectedAccessRoles);
	}

	@Test(dataProvider = "dataToStringArrayAsNull")
	public void testToStringArrayAsNull(final IAccessRole[] accessRoles) throws Exception {
		final TestEntity testEntity = new TestEntity();
		testEntity.setRoles(accessRoles);

		final TestEntity savedTestEntity = testEntityRepository.save(testEntity);
		Assert.assertEqualsNoOrder(savedTestEntity.getRoles(), testEntity.getRoles());
		Assert.assertEqualsNoOrder(savedTestEntity.getRoles(), accessRoles);

		final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from test_entity where id=?", savedTestEntity.getId());
		Assert.assertTrue(sqlRowSet.next());
		final String role = sqlRowSet.getString("roles");
		Assert.assertNull(role);
	}

	@Test(dataProvider = "dataToStringElementCollectionList")
	public void testToStringElementCollectionList(final List<IAccessRole> accessRoles) throws Exception {
		final TestEntity testEntity = new TestEntity();
		testEntity.setRoleList(accessRoles);

		final TestEntity savedTestEntity = testEntityRepository.save(testEntity);
		Assert.assertEquals(savedTestEntity.getRoleList(), testEntity.getRoleList());
		Assert.assertEquals(savedTestEntity.getRoleList(), accessRoles);

		final List<String> list = jdbcTemplate.queryForList("select roleList from TestEntity_roleList where TestEntity_id=?", String.class, savedTestEntity.getId());
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), accessRoles.size());
		for (IAccessRole accessRole : accessRoles) {
			Assert.assertTrue(list.contains(accessRole.getFullName()));
		}
	}

	@Test(dataProvider = "dataToStringElementCollectionSet")
	public void testToStringElementCollectionSet(final List<IAccessRole> accessRoles, final int expectedCollectionSize) throws Exception {
		final TestEntity testEntity = new TestEntity();
		testEntity.setRoleSet(new HashSet<>(accessRoles));

		final TestEntity savedTestEntity = testEntityRepository.save(testEntity);
		Assert.assertEquals(savedTestEntity.getRoleList(), testEntity.getRoleList());
		Assert.assertNotEquals(savedTestEntity.getRoleList(), accessRoles);

		final List<String> list = jdbcTemplate.queryForList("select roleSet from TestEntity_roleSet where TestEntity_id=?", String.class, savedTestEntity.getId());
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), expectedCollectionSize);
		for (IAccessRole accessRole : accessRoles) {
			Assert.assertTrue(list.contains(accessRole.getFullName()));
		}
	}

	@Test(dataProvider = "dataToStringOne")
	public void testToStringOne(final IAccessRole accessRole, final String expectedAccessRole) throws Exception {
		final TestEntity testEntity = new TestEntity();
		testEntity.setRole(accessRole);

		final TestEntity savedTestEntity = testEntityRepository.save(testEntity);
		Assert.assertEquals(savedTestEntity.getRole(), testEntity.getRole());
		Assert.assertEquals(savedTestEntity.getRole(), accessRole);

		final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from test_entity where id=?", savedTestEntity.getId());
		Assert.assertTrue(sqlRowSet.next());
		final String role = sqlRowSet.getString("role");
		Assert.assertEquals(role, expectedAccessRole);
	}

	@Test
	public void testToStringOneAsNull() throws Exception {
		final TestEntity testEntity = new TestEntity();

		final TestEntity savedTestEntity = testEntityRepository.save(testEntity);
		Assert.assertEquals(savedTestEntity.getRole(), testEntity.getRole());
		Assert.assertEquals(savedTestEntity.getRole(), null);

		final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from test_entity where id=?", savedTestEntity.getId());
		Assert.assertTrue(sqlRowSet.next());
		final String role = sqlRowSet.getString("role");
		Assert.assertNull(role);
	}
}