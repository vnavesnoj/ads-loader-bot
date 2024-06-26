package vnavesnoj.ads_loader_bot_service.factory;

import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_service.service.component.AdAnalyzer;

import java.util.List;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface AnalyzerFactory {

    List<AdAnalyzer> getAdAnalyzers();

    AdAnalyzer getAdAnalyzer(AnalyzerEnum analyzer);
}
