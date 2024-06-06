package vnavesnoj.ads_loader_bot_service.service.component;

import vnavesnoj.ads_loader_bot_service.service.component.impl.Html;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface AdsJsonScanner<A> {

    A findAds(Html html);
}
