package vnavesnoj.ads_loader_bot_service.service.component;

import java.util.List;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface AdsJsonParser<A, J> {

    List<A> parse(J adsJson);
}
