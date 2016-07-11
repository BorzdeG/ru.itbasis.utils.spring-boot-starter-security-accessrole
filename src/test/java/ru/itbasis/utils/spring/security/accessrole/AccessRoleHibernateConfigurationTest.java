package ru.itbasis.utils.spring.security.accessrole;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.itbasis.utils.spring.security.accessrole.hibernate.type.AccessRoleUserType;
import sample.ru.itbasis.utils.spring.security.accessrole.TestBootApplication;

import javax.sql.DataSource;

public class AccessRoleHibernateConfigurationTest {

	@Test
	public void testAutoConfiguration() throws Exception {
		final SpringApplication              application        = new SpringApplication(TestBootApplication.class);
		final ConfigurableApplicationContext applicationContext = application.run();
		Assert.assertNotNull(applicationContext.getBean(AccessRoleUserType.class));
	}

	@Test(expectedExceptions = {NoSuchBeanDefinitionException.class})
	public void testNoAutoConfiguration() throws Exception {
		final SpringApplication              springApplication  = new SpringApplication(AccessRoleJpaAutoConfiguration.class, AccessRoleAutoConfiguration.class);
		final ConfigurableApplicationContext applicationContext = springApplication.run();

		Assert.assertNull(applicationContext.getBean(DataSource.class));
		final AccessRoleUserType accessRoleUserType = applicationContext.getBean(AccessRoleUserType.class);
		Assert.assertNull(accessRoleUserType);
	}

	@Test(expectedExceptions = {NoSuchBeanDefinitionException.class})
	public void testNoAutoConfigurationEmptyContext() throws Exception {
		final AnnotationConfigApplicationContext emptyContext = new AnnotationConfigApplicationContext();
		emptyContext.refresh();
		Assert.assertNull(emptyContext.getBean(AccessRoleUserType.class));
	}

	@Test(expectedExceptions = {NoSuchBeanDefinitionException.class})
	public void testNoAutoConfigurationPartialConfiguration() throws Exception {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AccessRoleAutoConfiguration.class);
		Assert.assertNotNull(context.getBean(AccessRoleUserType.class));
	}

}