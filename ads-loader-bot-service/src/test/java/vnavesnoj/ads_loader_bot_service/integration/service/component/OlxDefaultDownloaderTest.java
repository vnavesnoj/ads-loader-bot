package vnavesnoj.ads_loader_bot_service.integration.service.component;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import vnavesnoj.ads_loader_bot_common.database.entity.Analyzer;
import vnavesnoj.ads_loader_bot_common.database.entity.Platform;
import vnavesnoj.ads_loader_bot_common.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.integration.annotation.IT;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.OlxDefaultDownloader;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@IT
public class OlxDefaultDownloaderTest {

    private final OlxDefaultDownloader olxDefaultDownloader;

    @Test
    void test() {
        final var spot = new Spot(1, Platform.OLX, "transport/legkovye-avtomobili/", "Авто", Analyzer.OLX_DEFAULT);
        final var downloaded = olxDefaultDownloader.downloadAds(spot);
        System.out.println();
    }
}
