package ru.itbasis.utils.spring.security.accessrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import sample.ru.itbasis.utils.spring.security.accessrole.TestBootApplication;

@SpringBootTest(classes = {TestBootApplication.class})
public abstract class AbstractCoreTests extends AbstractTestNGSpringContextTests {
	protected static final GrantedAuthority GRANTED_AUTHORITY_CORE_GUEST   = new SimpleGrantedAuthority("ROLE_CORE_GUEST");
	protected static final GrantedAuthority GRANTED_AUTHORITY_CORE_ADMIN   = new SimpleGrantedAuthority("ROLE_CORE_ADMIN");
	protected static final GrantedAuthority GRANTED_AUTHORITY_CUSTOM_GUEST = new SimpleGrantedAuthority("ROLE_CUSTOM_GUEST");

	@Autowired
	protected ConversionService conversionService;
}
