package vnavesnoj.ads_loader_bot_service.validator;

import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface JsonPatternValidator {

    default boolean fieldExists(AnalyzerEnum analyzer, String field) {
        return Arrays.stream(analyzer.getPatternClass().getDeclaredFields())
                .map(Field::getName)
                .anyMatch(item -> item.equals(field));
    }

    String validateJsonPattern(String jsonPattern, AnalyzerEnum analyzer);
}
