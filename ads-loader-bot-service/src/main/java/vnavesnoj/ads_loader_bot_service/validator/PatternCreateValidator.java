package vnavesnoj.ads_loader_bot_service.validator;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterBuilderRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.exception.FilterBuilderAlreadyExistsException;
import vnavesnoj.ads_loader_bot_service.exception.PatternValidationException;
import vnavesnoj.ads_loader_bot_service.validator.component.PatternValidatorHelper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class PatternCreateValidator implements ObjectValidator<FilterBuilderCreateDto> {

    private final PatternValidatorHelper patternValidatorHelper;
    private final FilterBuilderRepository filterBuilderRepository;
    private final Validator validator;

    @Override
    public FilterBuilderCreateDto validate(FilterBuilderCreateDto object) throws PatternValidationException {
        patternValidatorHelper.checkPatternType(object.getPattern(), object.getSpotId());
        filterBuilderRepository.findByUserId(object.getUserId())
                .ifPresent(item -> {
                    throw new FilterBuilderAlreadyExistsException("FilterBuilder with user.id = %s already exists"
                            .formatted(object.getUserId()));
                });
        patternValidatorHelper.validatePattern(object.getPattern());
        return object;
    }
}
