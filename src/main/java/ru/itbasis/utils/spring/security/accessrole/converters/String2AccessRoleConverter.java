package ru.itbasis.utils.spring.security.accessrole.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.Converter;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;
import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRoleConverter;

import static org.apache.commons.lang3.ClassUtils.getSimpleName;

@Slf4j
@AccessRoleConverter
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public final class String2AccessRoleConverter implements Converter<String, IAccessRole>, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public IAccessRole convert(final String source) {
		log.trace("source: {}", source);

		@SuppressWarnings("unchecked")
		final IAccessRole target = (IAccessRole) applicationContext.getBean(IAccessRole.BEAN_PREFIX + source);
		if (log.isTraceEnabled()) {
			log.trace("target class: {}", getSimpleName(target, null));
			log.trace("target: {}", target);
		}
		return target;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
