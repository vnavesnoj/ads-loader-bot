package vnavesnoj.ads_loader_bot_service.exception;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public class PatternValidationException extends ObjectValidationException {

    public PatternValidationException(Set<ConstraintViolation<Object>> errors) {
        super(errors);
    }
}
