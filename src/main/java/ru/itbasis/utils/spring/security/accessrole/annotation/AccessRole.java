package ru.itbasis.utils.spring.security.accessrole.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ANNOTATION_TYPE, TYPE})
public @interface AccessRole {
}
