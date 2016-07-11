package sample.ru.itbasis.utils.spring.security.accessrole;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import ru.itbasis.utils.spring.security.accessrole.AccessRoleJpaAutoConfiguration;
import sample.ru.itbasis.utils.spring.security.accessrole.entity.TestEntity;
import sample.ru.itbasis.utils.spring.security.accessrole.repository.TestEntityRepository;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@DataJpaTest
@Configuration
@ComponentScan
@ImportAutoConfiguration(AccessRoleJpaAutoConfiguration.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@EnableJpaRepositories(basePackageClasses = TestEntityRepository.class)
public class TestBootApplication {

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

		bean.setPackagesToScan(TestEntity.class.getPackage().getName());
		bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		bean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

		final Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.dialect", H2Dialect.class.getName());
		jpaProperties.setProperty("hibernate.connection.url", EmbeddedDatabaseConnection.H2.getUrl());
		jpaProperties.setProperty("hibernate.connection.username", "sa");
		jpaProperties.setProperty("hibernate.connection.password", "");
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		jpaProperties.setProperty("hibernate.show_sql", "true");
		bean.setJpaProperties(jpaProperties);

		return bean;
	}

	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
