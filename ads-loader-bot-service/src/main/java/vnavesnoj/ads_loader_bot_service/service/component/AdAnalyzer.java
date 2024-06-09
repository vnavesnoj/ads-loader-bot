package vnavesnoj.ads_loader_bot_service.service.component;

import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_common.database.entity.FilterAd;
import vnavesnoj.ads_loader_bot_common.database.entity.Spot;

import java.util.List;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface AdAnalyzer {

    List<FilterAd> findNewFilterAd(Spot spot, List<Filter> filters);
}
