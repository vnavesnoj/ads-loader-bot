package vnavesnoj.ads_loader_bot_common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vnavesnoj.ads_loader_bot_common.validation.MaxLengthOfElementsValidator;

import java.lang.annotation.*;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Documented
@Constraint(validatedBy = MaxLengthOfElementsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLengthOfElements {

    int value();

    String message() default "{bot.validation.constraint.MaxLengthOfElements.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
