package vnavesnoj.ads_loader_bot_service.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderEditDto;
import vnavesnoj.ads_loader_bot_service.exception.ObjectValidationException;
import vnavesnoj.ads_loader_bot_service.validator.component.ValidatorHelper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class PatternEditValidator implements ObjectValidator<FilterBuilderEditDto> {

    private final ValidatorHelper validatorHelper;

    @Override
    public FilterBuilderEditDto validate(FilterBuilderEditDto object) throws ObjectValidationException {
        validatorHelper.checkPatternType(object.getPattern(), object.getSpotId());
        validatorHelper.validatePattern(object.getPattern());
        return object;
    }
}
