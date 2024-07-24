package vnavesnoj.ads_loader_bot_service.validator.component;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.database.repository.SpotRepository;
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
public class ValidatorHelperImpl implements ValidatorHelper {

    private final SpotRepository spotRepository;
    private final Validator validator;

    @Override
    public void checkPatternType(Object pattern, Integer spotId) throws SpotNotExistsException, PatternCastException {
        final var spot = spotRepository.findById(spotId)
                .orElseThrow(() ->
                        new SpotNotExistsException("spot with id = %s not exists".formatted(spotId))
                );
        Optional.of(spot)
                .map(Spot::getAnalyzer)
                .map(AnalyzerEnum::getPatternClass)
                .filter(clazz -> clazz.isInstance(pattern))
                .orElseThrow(() ->
                        new PatternCastException(
                                "the class of the pattern %s is not a type of the spot.id = %s"
                                        .formatted(pattern, spot.getId())
                        )
                );
    }

    @Override
    public void validatePattern(Object pattern) throws PatternValidationException {
        final var errors = validator.validate(pattern);
        if (!errors.isEmpty()) {
            throw new PatternValidationException(errors);
        }
    }
}
