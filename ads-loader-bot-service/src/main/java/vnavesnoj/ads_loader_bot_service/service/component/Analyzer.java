package vnavesnoj.ads_loader_bot_service.service.component;

import vnavesnoj.ads_loader_bot_common.database.entity.Ad;

import java.net.URL;
import java.util.List;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface Analyzer<A, F> {

    List<A> downloadAds(URL url);

    List<A> filter(List<A> ads, F filter);

    List<Ad> mapToEntity(List<A> ads);
}
