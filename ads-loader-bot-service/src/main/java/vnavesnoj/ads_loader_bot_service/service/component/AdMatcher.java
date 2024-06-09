package vnavesnoj.ads_loader_bot_service.service.component;

import vnavesnoj.ads_loader_bot_common.database.entity.Filter;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface AdMatcher<A> {

    boolean match(A ad, Filter filter);
}
