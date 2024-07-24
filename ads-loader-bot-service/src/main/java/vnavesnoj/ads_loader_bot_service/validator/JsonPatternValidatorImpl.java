package vnavesnoj.ads_loader_bot_service.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_service.exception.PatternCastException;
import vnavesnoj.ads_loader_bot_service.validator.component.PatternValidatorHelper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class JsonPatternValidatorImpl implements JsonPatternValidator {

    private final PatternValidatorHelper patternValidatorHelper;
    private final ObjectMapper objectMapper;

    @Override
    public String validateJsonPattern(String jsonPattern, AnalyzerEnum analyzer) {
        final Object pattern;
        try {
            pattern = objectMapper.readValue(jsonPattern, analyzer.getPatternClass());
        } catch (JsonProcessingException e) {
            throw new PatternCastException(e);
        }
        patternValidatorHelper.validatePattern(pattern);
        return jsonPattern;
    }
}
