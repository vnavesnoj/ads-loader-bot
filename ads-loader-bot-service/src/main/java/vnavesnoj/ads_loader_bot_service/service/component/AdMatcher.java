package vnavesnoj.ads_loader_bot_service.service.component;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface AdMatcher<A, F> {

    boolean match(A ad, F filter);
}
