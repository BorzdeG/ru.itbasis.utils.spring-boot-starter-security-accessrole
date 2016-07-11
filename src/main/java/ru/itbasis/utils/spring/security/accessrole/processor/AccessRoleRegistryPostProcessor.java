package ru.itbasis.utils.spring.security.accessrole.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRole;
import ru.itbasis.utils.spring.security.accessrole.converters.AccessRole2StringConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.context.ConfigurableApplicationContext.CONVERSION_SERVICE_BEAN_NAME;

@Slf4j
public final class AccessRoleRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	private Map<String, IAccessRole> accessRoles = new HashMap<>();

	private MetadataReaderFactory          metadataReaderFactory;
	private BeanDefinitionRegistry         registry;
	private Converter<IAccessRole, String> converter;

	public AccessRoleRegistryPostProcessor() {
		metadataReaderFactory = new SimpleMetadataReaderFactory();
		this.converter = new AccessRole2StringConverter();
	}

	private boolean isAccessRole(final String beanClassName) {
		log.debug("beanClassName: {}", beanClassName);
		try {
			final MetadataReader     metadataReader     = metadataReaderFactory.getMetadataReader(beanClassName);
			final AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
			return annotationMetadata.isAnnotated(AccessRole.class.getName());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(final BeanDefinitionRegistry registry) throws BeansException {
		this.registry = registry;
		registry.registerBeanDefinition(CONVERSION_SERVICE_BEAN_NAME,
																		BeanDefinitionBuilder.rootBeanDefinition(ConversionServiceFactoryBean.class).getBeanDefinition());


		final String[] beanDefinitionNames = this.registry.getBeanDefinitionNames();
		Arrays.stream(beanDefinitionNames)
					.parallel()
					.forEach(beanDefinitionName -> {
						log.debug("beanDefinitionName: {}", beanDefinitionName);
						final BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
						final String         beanClassName  = beanDefinition.getBeanClassName();
						if (StringUtils.trimToEmpty(beanClassName).isEmpty()) {return;}

						if (!isAccessRole(beanClassName)) { return;}
						processAccessRole(beanClassName, beanDefinitionName);
					});
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
		accessRoles.forEach((key, role) -> beanFactory.registerSingleton(IAccessRole.BEAN_PREFIX + key, role));
	}

	private void processAccessRole(final String beanClassName, final String beanDefinitionName) throws BeansException {
		final Class<?> beanClass = ClassUtils.resolveClassName(beanClassName, ClassUtils.getDefaultClassLoader());
		log.debug("beanClass: {}", beanClass);

		if (!IAccessRole.class.isAssignableFrom(beanClass)) {
			throw new BeanDefinitionValidationException("AccessRole bean not implements IRole interface: " + beanClass);
		}
		log.debug("bean from @AccessRole: {}", beanClass);

		if (beanClass.isEnum()) {
			log.trace("enum: {}", beanClass);
			processAccessRoleFromEnumType(beanDefinitionName, beanClass);
		}
	}

	private void processAccessRoleFromEnumType(final String beanDefinitionName, final Class beanClass) {
		log.debug("bean enum type: {}", beanClass);
		registry.removeBeanDefinition(beanDefinitionName);

		Stream.of(beanClass.getEnumConstants())
					.forEach(it -> {
						log.debug("it: {}", it);
						Assert.isTrue(it instanceof IAccessRole);

						@SuppressWarnings("ConstantConditions") final IAccessRole role = (IAccessRole) it;

						final String roleName = converter.convert(role);
						log.debug("roleName: {}", roleName);
						accessRoles.put(roleName, role);
					});
	}
}
