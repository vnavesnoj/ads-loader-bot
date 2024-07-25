package vnavesnoj.ads_loader_bot_service.validator.component;

import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_service.exception.PatternCastException;
import vnavesnoj.ads_loader_bot_service.exception.PatternValidationException;
import vnavesnoj.ads_loader_bot_service.exception.SpotNotExistsException;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface PatternValidatorHelper {

    void checkPatternType(Object pattern, Integer spotId) throws SpotNotExistsException, PatternCastException;

    void validatePattern(Object pattern) throws PatternValidationException;

    void validatePatternField(Object pattern, String field) throws PatternValidationException;

    boolean fieldExists(AnalyzerEnum analyzer, String field);

    String validateJsonPattern(String jsonPattern, AnalyzerEnum analyzer);
}
