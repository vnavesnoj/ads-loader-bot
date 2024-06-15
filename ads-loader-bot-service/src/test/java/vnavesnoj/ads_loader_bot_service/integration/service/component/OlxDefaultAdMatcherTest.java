package vnavesnoj.ads_loader_bot_service.integration.service.component;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import vnavesnoj.ads_loader_bot_common.database.entity.*;
import vnavesnoj.ads_loader_bot_service.annotation.IT;
import vnavesnoj.ads_loader_bot_service.config.TestContainersConfig;
import vnavesnoj.ads_loader_bot_service.service.component.AdMatcher;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdBody;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Import(TestContainersConfig.class)
@IT
public class OlxDefaultAdMatcherTest {

    private final AdMatcher<OlxDefaultAdBody> olxDefaultAdMatcher;

    private final ZonedDateTime nowZoned = ZonedDateTime.now();
    private final Instant nowInstant = Instant.now();

    private final OlxDefaultAdBody adBody1 = new OlxDefaultAdBody(
            1L,
            "dummy title",
            "Dummy description",
            "dummy url",
            nowZoned,
            nowZoned,
            nowZoned,
            true,
            false,
            false,
            123L,
            "UAH",
            "dummy city",
            "dummy region"
    );

    private final Spot spot1 = new Spot(
            1,
            Platform.OLX,
            "dummy url",
            "dummy name",
            Analyzer.OLX_DEFAULT
    );

    private final String pattern1 = """
            {
            "descriptionPatterns": ["description"],
            "priceType": "ALL",
            "minPrice": 0,
            "maxPrice": 1000,
            "currencyCode": "UAH",
            "cityNames": ["city"],
            "regionNames": ["region"]
            }""";

    private final String pattern2 = """
            {
            "descriptionPatterns": ["description"],
            "priceType": "ALL",
            "minPrice": 0,
            "maxPrice": 1000,
            "currencyCode": "UAH"
            }""";

    private final String pattern3 = """
            {
            "descriptionPatterns": ["description"],
            "priceType": "PAID",
            "minPrice": 0,
            "maxPrice": 1000,
            "currencyCode": "UAH"
            }""";

    private final String pattern4 = """
            {
            "descriptionPatterns": ["description"],
            "priceType": "PAID",
            "minPrice": 0,
            "maxPrice": 1000
            }""";

    private final String pattern5 = """
            {
            "descriptionPatterns": ["description"],
            "priceType": "PAID",
            "minPrice": 0
            }""";

    private final String pattern6 = """
            {
            "descriptionPatterns": ["description"],
            "priceType": "PAID",
            "maxPrice": 1000
            }""";

    private final String pattern7 = """
            {
            "descriptionPatterns": ["description"],
            "priceType": "PAID"
            }""";

    private final String pattern8 = """
            {
            "descriptionPatterns": ["description"]
            }""";

    private final String pattern9 = """
            {
            "descriptionPatterns": ["xyz"]
            }""";

    private final String pattern10 = """
            {"priceType": "ALL",
            "minPrice": 0,
            "maxPrice": 1000,
            "currencyCode": "UAH",
            "cityNames": ["city"],
            "regionNames": ["region"]
            }""";

    private final String pattern11 = """
            {"minPrice": 0,
            "maxPrice": 1000,
            "currencyCode": "UAH",
            "cityNames": ["city"],
            "regionNames": ["region"]
            }""";

    private final String pattern12 = """
            {"maxPrice": 1000,
            "currencyCode": "UAH",
            "cityNames": ["city"],
            "regionNames": ["region"]
            }""";

    private final String pattern13 = """
            {"currencyCode": "UAH",
            "cityNames": ["city"],
            "regionNames": ["region"]
            }""";

    private final String pattern14 = """
            {"cityNames": ["city"],
            "regionNames": ["region"]
            }""";

    private final String pattern15 = """
            {"regionNames": ["region"]
            }""";

    private final String pattern16 = """
            {"regionNames": ["xyz"]
            }""";

    private final User user1 = new User(
            1L,
            "dummy code",
            nowInstant,
            true
    );

    private final Filter filter1 = new Filter(
            1L,
            "dummy name",
            "dummy description",
            nowInstant,
            spot1,
            true,
            pattern1,
            user1
    );

    @Test
    void trueWhenMatch() {
        final var actual = olxDefaultAdMatcher.match(adBody1, filter1);
        assertThat(actual).isTrue();

        filter1.setJsonPattern(pattern2);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern3);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern4);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern5);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern6);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern7);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern8);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern9);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isFalse();

        filter1.setJsonPattern(pattern1);
        filter1.setEnabled(false);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isFalse();

        filter1.setEnabled(true);
        filter1.setJsonPattern(pattern10);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern11);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern12);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern13);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern14);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern15);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isTrue();

        filter1.setJsonPattern(pattern16);
        assertThat(olxDefaultAdMatcher.match(adBody1, filter1)).isFalse();
    }
}
