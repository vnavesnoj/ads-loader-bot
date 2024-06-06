package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.service.component.AdsJsonParser;
import vnavesnoj.ads_loader_bot_service.service.component.AdsJsonScanner;
import vnavesnoj.ads_loader_bot_service.service.component.Connector;
import vnavesnoj.ads_loader_bot_service.service.component.SpotDownloader;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdBody;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdsJson;

import java.util.List;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultDownloader implements SpotDownloader<OlxDefaultAdBody> {

    private final Connector jsoupConnector;
    private final AdsJsonScanner<OlxDefaultAdsJson> olxDefaultAdsJsonScanner;
    private final AdsJsonParser<OlxDefaultAdBody, OlxDefaultAdsJson> olxDefaultAdsJsonParser;

    //TODO add url postfix
    @Override
    public List<OlxDefaultAdBody> download(@NonNull Spot spot) {
        return Optional.of(spot)
                .map(Spot::getUrl)
                .map(url -> jsoupConnector.getHtml("https://" + spot.getUrl()))
                .map(olxDefaultAdsJsonScanner::findAds)
                .map(olxDefaultAdsJsonParser::parse)
                .orElseThrow();
    }
}
