package vnavesnoj.ads_loader_bot_web.factory.builderassistant;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_service.util.EnumImplementationAnalyzer;
import vnavesnoj.ads_loader_bot_web.assistant.FilterBuilderAssistant;

import java.util.List;
import java.util.Map;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class FilterBuilderAssistantFactoryImpl implements FilterBuilderAssistantFactory {

    private final Map<AnalyzerEnum, FilterBuilderAssistant> assistants;

    public FilterBuilderAssistantFactoryImpl(List<FilterBuilderAssistant> assistants,
                                             EnumImplementationAnalyzer analyzer) {
        this.assistants = analyzer.getUniqueEnumImplementations(
                AnalyzerEnum.class,
                assistants,
                FilterBuilderAssistant::getAnalyzer
        );
    }

    @Override
    public FilterBuilderAssistant getAssistant(AnalyzerEnum analyzer) {
        return assistants.get(analyzer);
    }
}
