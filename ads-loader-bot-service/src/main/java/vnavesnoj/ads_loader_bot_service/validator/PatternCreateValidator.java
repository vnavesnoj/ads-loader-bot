package vnavesnoj.ads_loader_bot_service.validator;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterBuilderRepository;
import vnavesnoj.ads_loader_bot_service.database.repository.SpotRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.exception.FilterBuilderAlreadyExistsException;
import vnavesnoj.ads_loader_bot_service.exception.PatternCastException;
import vnavesnoj.ads_loader_bot_service.exception.PatternValidationException;
import vnavesnoj.ads_loader_bot_service.exception.SpotNotExistsException;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class PatternCreateValidator implements ObjectValidator<FilterBuilderCreateDto> {

    private final SpotRepository spotRepository;
    private final FilterBuilderRepository filterBuilderRepository;
    private final Validator validator;

    @Override
    public FilterBuilderCreateDto validate(FilterBuilderCreateDto object) throws PatternValidationException {
        final var spot = spotRepository.findById(object.getSpotId())
                .orElseThrow(() ->
                        new SpotNotExistsException("spot with id = %s not exists".formatted(object.getSpotId()))
                );
        filterBuilderRepository.findByUserId(object.getUserId())
                .ifPresent(item -> {
                    throw new FilterBuilderAlreadyExistsException("FilterBuilder with user.id = %s already exists"
                            .formatted(object.getUserId()));
                });
        Optional.of(spot)
                .map(Spot::getAnalyzer)
                .map(AnalyzerEnum::getPatternClass)
                .filter(clazz -> clazz.isInstance(object.getPattern()))
                .orElseThrow(() ->
                        new PatternCastException(
                                "the class of the pattern in the %s is not a type of the spot.id = %s"
                                        .formatted(object, spot.getId())
                        )
                );
        final var errors = validator.validate(object.getPattern());
        if (errors.isEmpty()) {
            return object;
        } else {
            throw new PatternValidationException(errors);
        }
    }
}
