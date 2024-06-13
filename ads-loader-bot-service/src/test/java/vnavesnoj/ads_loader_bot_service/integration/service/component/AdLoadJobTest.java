package vnavesnoj.ads_loader_bot_service.integration.service.component;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import vnavesnoj.ads_loader_bot_service.annotation.IT;
import vnavesnoj.ads_loader_bot_service.service.AdLoadJob;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@IT
public class AdLoadJobTest {

    private final AdLoadJob adLoadJob;

    @Disabled
    @Rollback(value = false)
    @Test
    void test() {
        adLoadJob.loadNewFilterAds();
//        adLoadJob.loadNewFilterAds();
//        adLoadJob.loadNewFilterAds();
    }
}