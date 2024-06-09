package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.service.component.AdMatcher;
import vnavesnoj.ads_loader_bot_service.service.component.AnalyzeComponentsFactory;
import vnavesnoj.ads_loader_bot_service.service.component.SpotDownloader;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdBody;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultAnalyzeComponentsFactory implements AnalyzeComponentsFactory<OlxDefaultAdBody> {

    private final OlxDefaultDownloader olxDefaultDownloader;
    private final OlxDefaultAdMatcher olxDefaultAdMatcher;

    @Override
    public SpotDownloader<OlxDefaultAdBody> getSpotDownloader() {
        return olxDefaultDownloader;
    }

    @Override
    public AdMatcher<OlxDefaultAdBody> getAdMatcher() {
        return olxDefaultAdMatcher;
    }
}
