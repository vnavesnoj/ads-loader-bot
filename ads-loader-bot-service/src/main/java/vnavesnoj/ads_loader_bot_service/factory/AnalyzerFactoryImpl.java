package vnavesnoj.ads_loader_bot_service.factory;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_service.service.component.AdAnalyzer;
import vnavesnoj.ads_loader_bot_service.util.EnumImplementationAnalyzer;

import java.util.List;
import java.util.Map;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class AnalyzerFactoryImpl implements AnalyzerFactory {

    private final Map<AnalyzerEnum, AdAnalyzer> adAnalyzers;

    //TODO need tests in future
    public AnalyzerFactoryImpl(List<AdAnalyzer> adAnalyzers,
                               EnumImplementationAnalyzer enumAnalyzer) {
        this.adAnalyzers = enumAnalyzer.getUniqueEnumImplementations(
                AnalyzerEnum.class,
                adAnalyzers,
                AdAnalyzer::getAnalyzerName
        );
    }

    @Override
    public List<AdAnalyzer> getAdAnalyzers() {
        return adAnalyzers.values().stream().toList();
    }

    @Override
    public AdAnalyzer getAdAnalyzer(AnalyzerEnum analyzer) {
        return adAnalyzers.get(analyzer);
    }
}
