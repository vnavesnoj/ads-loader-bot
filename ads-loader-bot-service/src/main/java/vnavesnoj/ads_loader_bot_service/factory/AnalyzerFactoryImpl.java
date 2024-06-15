package vnavesnoj.ads_loader_bot_service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Platform;
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

    private final Map<Platform, AdAnalyzer> adAnalyzers;

    //TODO need tests in future
    public AnalyzerFactoryImpl(@Autowired List<AdAnalyzer> adAnalyzers) {
        final var duplicateImplementations = getDuplicateImplementations(adAnalyzers);
        final var notImplementedPlatforms = Arrays.stream(Platform.values())
                .filter(platform -> adAnalyzers.stream()
                        .noneMatch(analyzer -> analyzer.getAnalysisPlatform() == platform))
                .toList();
        if (notImplementedPlatforms.isEmpty() && duplicateImplementations.isEmpty()) {
            this.adAnalyzers = new HashMap<>();
            adAnalyzers.forEach(item -> this.adAnalyzers.put(item.getAnalysisPlatform(), item));
        } else if (duplicateImplementations.isEmpty()) {
            throw new AnalyzerInitializationException("there are not implemented platforms: " + notImplementedPlatforms);
        } else {
            final var duplications = duplicateImplementations.stream()
                    .map(item -> item.getClass().getName() + "(" + item.getAnalysisPlatform() + ")")
                    .collect(Collectors.joining(",", "[", "]"));
            throw new AnalyzerInitializationException("there are duplicated implementations : " + duplications);
        }
    }

    @Override
    public List<AdAnalyzer> getAdAnalyzers() {
        return adAnalyzers.values().stream().toList();
    }

    @Override
    public AdAnalyzer getAdAnalyzer(Platform platform) {
        return adAnalyzers.get(platform);
    }

    private List<AdAnalyzer> getDuplicateImplementations(List<AdAnalyzer> analyzers) {
        Set<Platform> platforms = new HashSet<>();
        return analyzers.stream()
                .filter(not(item -> platforms.add(item.getAnalysisPlatform())))
                .distinct()
                .toList();
    }
}
