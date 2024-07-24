package vnavesnoj.ads_loader_bot_service.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderEditDto;
import vnavesnoj.ads_loader_bot_service.exception.ObjectValidationException;
import vnavesnoj.ads_loader_bot_service.validator.component.PatternValidatorHelper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class PatternEditValidator implements ObjectValidator<FilterBuilderEditDto> {

    private final PatternValidatorHelper patternValidatorHelper;

    @Override
    public FilterBuilderEditDto validate(FilterBuilderEditDto object) throws ObjectValidationException {
        patternValidatorHelper.checkPatternType(object.getPattern(), object.getSpotId());
        patternValidatorHelper.validatePattern(object.getPattern());
        return object;
    }
}
