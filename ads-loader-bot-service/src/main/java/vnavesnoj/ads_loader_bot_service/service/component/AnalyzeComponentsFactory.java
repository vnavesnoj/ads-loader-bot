package vnavesnoj.ads_loader_bot_service.service.component;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface AnalyzeComponentsFactory<A> {

    SpotDownloader<A> getSpotDownloader();

    AdMatcher<A> getAdMatcher();
}
