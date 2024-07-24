package vnavesnoj.ads_loader_bot_service.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import lombok.Getter;

import java.util.Set;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Getter
public class PatternValidationException extends ValidationException {

    Set<ConstraintViolation<Object>> errors;

    public PatternValidationException(Set<ConstraintViolation<Object>> errors) {
        super();
        this.errors = errors;
    }
}
