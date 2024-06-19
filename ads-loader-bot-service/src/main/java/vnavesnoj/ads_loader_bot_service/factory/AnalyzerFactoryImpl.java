package vnavesnoj.ads_loader_bot_service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_service.exception.AnalyzerInitializationException;
import vnavesnoj.ads_loader_bot_service.service.component.AdAnalyzer;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class AnalyzerFactoryImpl implements AnalyzerFactory {

    private final Map<AnalyzerEnum, AdAnalyzer> adAnalyzers;

    //TODO need tests in future
    public AnalyzerFactoryImpl(@Autowired List<AdAnalyzer> adAnalyzers) {
        final var duplicateImplementations = getDuplicateImplementations(adAnalyzers);
        final var notImplementedAnalyzers = Arrays.stream(AnalyzerEnum.values())
                .filter(item -> adAnalyzers.stream()
                        .noneMatch(analyzer -> analyzer.getAnalyzerName() == item))
                .toList();
        if (notImplementedAnalyzers.isEmpty() && duplicateImplementations.isEmpty()) {
            this.adAnalyzers = new HashMap<>();
            adAnalyzers.forEach(item -> this.adAnalyzers.put(item.getAnalyzerName(), item));
        } else if (duplicateImplementations.isEmpty()) {
            throw new AnalyzerInitializationException("there are not implemented platforms: " + notImplementedAnalyzers);
        } else {
            final var duplications = duplicateImplementations.stream()
                    .map(item -> item.getClass().getName() + "(" + item.getAnalyzerName() + ")")
                    .collect(Collectors.joining(",", "[", "]"));
            throw new AnalyzerInitializationException("there are duplicated implementations: " + duplications);
        }
    }

    @Override
    public List<AdAnalyzer> getAdAnalyzers() {
        return adAnalyzers.values().stream().toList();
    }

    @Override
    public AdAnalyzer getAdAnalyzer(AnalyzerEnum analyzer) {
        return adAnalyzers.get(analyzer);
    }

    private List<AdAnalyzer> getDuplicateImplementations(List<AdAnalyzer> analyzers) {
        Set<AnalyzerEnum> analyzerEnums = new HashSet<>();
        return analyzers.stream()
                .filter(not(item -> analyzerEnums.add(item.getAnalyzerName())))
                .distinct()
                .toList();
    }
}
