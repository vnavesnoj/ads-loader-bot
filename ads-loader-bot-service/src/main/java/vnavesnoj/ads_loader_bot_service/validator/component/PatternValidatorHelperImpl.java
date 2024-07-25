package vnavesnoj.ads_loader_bot_service.validator.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.database.repository.SpotRepository;
import vnavesnoj.ads_loader_bot_service.exception.PatternCastException;
import vnavesnoj.ads_loader_bot_service.exception.PatternValidationException;
import vnavesnoj.ads_loader_bot_service.exception.SpotNotExistsException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class PatternValidatorHelperImpl implements PatternValidatorHelper {

    private final SpotRepository spotRepository;
    private final ObjectMapper objectMapper;
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

    @Override
    public void validatePatternField(Object pattern, String field) {
        final var errors = validator.validateProperty(pattern, field);
        if (!errors.isEmpty()) {
            throw new PatternValidationException(errors);
        }
    }

    @Override
    public boolean fieldExists(AnalyzerEnum analyzer, String field) {
        return Arrays.stream(analyzer.getPatternClass().getDeclaredFields())
                .map(Field::getName)
                .anyMatch(item -> item.equals(field));
    }

    @Override
    public String validateJsonPattern(String jsonPattern, AnalyzerEnum analyzer) {
        final Object pattern;
        try {
            pattern = objectMapper.readValue(jsonPattern, analyzer.getPatternClass());
        } catch (JsonProcessingException e) {
            throw new PatternCastException(e);
        }
        validatePattern(pattern);
        return jsonPattern;
    }
}
