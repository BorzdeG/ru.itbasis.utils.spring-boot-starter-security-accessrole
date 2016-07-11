package ru.itbasis.utils.spring.security.accessrole.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConversionServiceFactory;
import org.springframework.core.convert.support.GenericConversionService;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class AccessRoleConversionServiceFactory implements InitializingBean, ApplicationContextAware {
	private final GenericConversionService conversionService;
	private       ApplicationContext       applicationContext;

	@Autowired
	public AccessRoleConversionServiceFactory(final ConversionService conversionService) {
		this.conversionService = (GenericConversionService) conversionService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		final Map<String, Object> converters = applicationContext.getBeansWithAnnotation(AccessRoleConverter.class);
		final Set<Object> set = converters.entrySet()
																			.parallelStream()
																			.map(Map.Entry::getValue)
																			.collect(Collectors.toSet());
		ConversionServiceFactory.registerConverters(set, conversionService);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
