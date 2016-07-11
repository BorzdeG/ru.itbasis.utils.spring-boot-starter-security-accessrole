package sample.ru.itbasis.utils.spring.security.accessrole.annotation;

import ru.itbasis.utils.spring.security.accessrole.annotation.AccessRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@AccessRole
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomAccessRole {
}
