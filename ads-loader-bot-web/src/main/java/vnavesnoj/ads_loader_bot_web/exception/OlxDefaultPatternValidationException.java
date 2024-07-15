package vnavesnoj.ads_loader_bot_web.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.NonNull;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;

import javax.validation.ValidationException;
import java.util.Set;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Getter
public class OlxDefaultPatternValidationException extends ValidationException {

    private final Set<ConstraintViolation<OlxDefaultPattern>> errors;

    public OlxDefaultPatternValidationException(@NonNull Set<ConstraintViolation<OlxDefaultPattern>> errors) {
        super();
        this.errors = errors;
    }

    public OlxDefaultPatternValidationException(String s,
                                                @NonNull Set<ConstraintViolation<OlxDefaultPattern>> errors) {
        super(s);
        this.errors = errors;
    }

    public OlxDefaultPatternValidationException(String s,
                                                Throwable throwable,
                                                @NonNull Set<ConstraintViolation<OlxDefaultPattern>> errors) {
        super(s, throwable);
        this.errors = errors;
    }

    public OlxDefaultPatternValidationException(Throwable throwable,
                                                @NonNull Set<ConstraintViolation<OlxDefaultPattern>> errors) {
        super(throwable);
        this.errors = errors;
    }
}
