package vnavesnoj.ads_loader_bot_web.assistant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxFilterBuilderAssistant implements FilterBuilderAssistant {

    private final AnalyzerEnum analyzer = AnalyzerEnum.OLX_UA_DEFAULT;

    @Override
    public AnalyzerEnum getAnalyzer() {
        return analyzer;
    }
}
