package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
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

    @Value("${app.remote-resource.olx.connection.url:https://www.olx.ua/uk/}")
    private String URL_PREFIX;
    @Value("${app.remote-resource.olx.connection.url-postfix:?search%5Border%5D=created_at%3Adesc}")
    private String URL_POSTFIX;

    @Override
    public List<OlxDefaultAdBody> downloadAds(@NonNull Spot spot) {
        return Optional.of(spot)
                .map(Spot::getUrl)
                .map(url -> jsoupConnector.getHtml(URL_PREFIX + spot.getUrl() + URL_POSTFIX))
                .map(olxDefaultAdsJsonScanner::findAds)
                .map(olxDefaultAdsJsonParser::parse)
                .orElseThrow();
    }
}
