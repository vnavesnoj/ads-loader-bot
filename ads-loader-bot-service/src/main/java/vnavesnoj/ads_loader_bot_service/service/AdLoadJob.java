package vnavesnoj.ads_loader_bot_service.service;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */

public interface AdLoadJob {

    void loadNewFilterAds();

    void stop();

    void start();

    void resetCycle();
}
