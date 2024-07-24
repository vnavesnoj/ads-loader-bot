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
public class ObjectValidationException extends ValidationException {

    private final Set<ConstraintViolation<Object>> errors;

    public ObjectValidationException(Set<ConstraintViolation<Object>> errors) {
        super();
        this.errors = errors;
    }
}
