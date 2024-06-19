package vnavesnoj.ads_loader_bot_common.constant;

import lombok.RequiredArgsConstructor;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultAdBody;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
public enum AnalyzerEnum {

    OLX_UA_DEFAULT(OlxDefaultPattern.class, OlxDefaultAdBody.class);

    private final Class<?> patternClass;

    private final Class<?> adBodyClass;
}
