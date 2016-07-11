package ru.itbasis.utils.spring.security.accessrole.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Component
public @interface AccessRoleConverter {
	@AliasFor(annotation = Component.class, attribute = "value")
	String value() default "";
}
