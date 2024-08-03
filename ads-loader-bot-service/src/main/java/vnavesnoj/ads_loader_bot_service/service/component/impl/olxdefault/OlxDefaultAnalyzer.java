package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_common.mapper.Mapper;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultAdBody;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Ad;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Filter;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterAd;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.service.component.AdAnalyzer;
import vnavesnoj.ads_loader_bot_service.service.component.AdMatcher;
import vnavesnoj.ads_loader_bot_service.service.component.SpotDownloader;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultAnalyzer implements AdAnalyzer {

    private final SpotDownloader<OlxDefaultAdBody> olxDefaultDownloader;
    private final AdMatcher<OlxDefaultAdBody> olxDefaultAdMatcher;
    private final Mapper<OlxDefaultAdBody, Ad> olxDefaultAdMapper;

    private final AnalyzerEnum analyzerName = AnalyzerEnum.OLX_UA_DEFAULT;

    public List<FilterAd> findNewFilterAd(Spot spot, List<Filter> filters) {
        final var ads = olxDefaultDownloader.downloadAds(spot);
        final List<FilterAd> filterAds = new ArrayList<>();
        for (Filter filter : filters) {
            for (OlxDefaultAdBody adBody : ads) {
                if (olxDefaultAdMatcher.match(adBody, filter)) {
                    filterAds.add(FilterAd.builder()
                            .filter(filter)
                            .ad(olxDefaultAdMapper.map(adBody))
                            .instant(Instant.now())
                            .build());
                }
            }
        }
        return filterAds;
    }

    @Override
    public AnalyzerEnum getAnalyzerName() {
        return this.analyzerName;
    }
}
