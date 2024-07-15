package vnavesnoj.ads_loader_bot_common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vnavesnoj.ads_loader_bot_common.annotation.MaxLengthOfElements;

import java.util.Arrays;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public class MaxLengthOfElementsValidator implements ConstraintValidator<MaxLengthOfElements, String[]> {

    private int maxLength;

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        return value == null
                || Arrays.stream(value)
                .allMatch(item -> item.length() <= maxLength);
    }

    @Override
    public void initialize(MaxLengthOfElements parameters) {
        maxLength = parameters.value();
    }
}
