package vnavesnoj.ads_loader_bot_web.factory.builderassistant;

import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_web.assistant.FilterBuilderAssistant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderAssistantFactory {

    FilterBuilderAssistant getAssistant(AnalyzerEnum analyzer);
}
