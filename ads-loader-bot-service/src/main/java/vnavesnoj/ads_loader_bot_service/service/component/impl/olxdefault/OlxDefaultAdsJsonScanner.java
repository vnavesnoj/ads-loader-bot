package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault;

import org.springframework.stereotype.Component;
import org.unbescape.json.JsonEscape;
import vnavesnoj.ads_loader_bot_service.exception.PatternNotFoundException;
import vnavesnoj.ads_loader_bot_service.service.component.AdsJsonScanner;
import vnavesnoj.ads_loader_bot_service.service.component.impl.Html;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdsJson;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class OlxDefaultAdsJsonScanner implements AdsJsonScanner<OlxDefaultAdsJson> {

    private static final String PATTERN_JSON_GROUP = "json";

    private static final Pattern ADS_PATTERN_IN_HTML = Pattern.compile(
            "window\\.__PRERENDERED_STATE__= \"(?<" + PATTERN_JSON_GROUP + ">.+?)\";\n *window\\.__TAURUS"
    );

    @Override
    public OlxDefaultAdsJson findAds(Html html) {
        final var matcher = ADS_PATTERN_IN_HTML.matcher(html.getHtml());
        if (matcher.find()) {
            final var ads = Optional.ofNullable(matcher.group("json"))
                    .map(JsonEscape::unescapeJson)
                    .orElseThrow(
                            () -> new PatternNotFoundException("not found json ads in html = " + html)
                    );
            return new OlxDefaultAdsJson(ads, html.getHref(), html.getConnectionTime());
        } else {
            throw new PatternNotFoundException(
                    "not found matches in html = %s by pattern %s"
                            .formatted(html, ADS_PATTERN_IN_HTML)
            );
        }
    }
}
