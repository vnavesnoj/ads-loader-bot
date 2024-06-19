package vnavesnoj.ads_loader_bot_service.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_persistence.database.entity.*;
import vnavesnoj.ads_loader_bot_service.annotation.IT;
import vnavesnoj.ads_loader_bot_service.config.TestContainersConfig;
import vnavesnoj.ads_loader_bot_service.database.repository.*;
import vnavesnoj.ads_loader_bot_service.service.component.AdAnalyzer;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Import(TestContainersConfig.class)
@IT
public class AdLoadJobTest {

    private final AdLoadJob adLoadJob;
    private final FilterRepository filterRepository;
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final FilterAdRepository filterAdRepository;
    private final AdRepository adRepository;

    @SpyBean
    private AdAnalyzer analyzer;

    private final Instant nowInstant = Instant.now();

    private Category category1 = new Category(
            null,
            Platform.OLXUA,
            "dummy name"
    );

    private Spot spot1 = new Spot(
            null,
            Platform.OLXUA,
            "dummy url",
            "dummy name",
            AnalyzerEnum.OLX_UA_DEFAULT,
            category1
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

    private User user1 = new User(
            1L,
            "code",
            nowInstant,
            true,
            ChatStateEnum.START
    );

    private Filter filter1 = new Filter(
            null,
            "dummy name",
            "dummy description",
            nowInstant,
            spot1,
            true,
            pattern1,
            user1
    );

    private Ad ad1 = new Ad(
            1L,
            Platform.OLXUA,
            "dummy url",
            "dummy title",
            nowInstant,
            nowInstant,
            1,
            null
    );

    private final AdBody adBody1 = new AdBody(
            1L,
            "{\"dummy\": \"dummy\"}",
            ad1
    );

    private FilterAd filterAd1 = new FilterAd(
            null,
            nowInstant,
            ad1,
            filter1
    );

    @BeforeEach
    void saveFilters() {
        ad1.setAdBody(adBody1);
        spot1 = spotRepository.save(spot1);
        user1 = userRepository.save(user1);
        filter1 = filterRepository.save(filter1);
        adLoadJob.resetCycle();
    }

    @Test
    void faNotExistsAdNotExists() {
        doReturn(List.of(filterAd1)).when(analyzer).findNewFilterAd(spot1, java.util.List.of(filter1));
//        when(analyzer.findNewFilterAd(spot1, java.util.List.of(filter1))).thenReturn(List.of(filterAd1));
        adLoadJob.loadNewFilterAds();
        assertThat(filterAdRepository.count()).isEqualTo(1L);
        assertThat(adRepository.count()).isEqualTo(1L);
        final var actual = filterAdRepository.findByFilterIdAndAdId(filter1.getId(), ad1.getId());
        assertThat(actual).isPresent().get().matches(fa ->
                fa.getFilter().equals(filter1) && fa.getAd().equals(ad1) && fa.getAd().getAdBody().equals(adBody1)
        );
    }

    @Test
    void faNotExistsAdExists() {
        adRepository.save(ad1);
        doReturn(List.of(filterAd1)).when(analyzer).findNewFilterAd(spot1, java.util.List.of(filter1));
//        when(analyzer.findNewFilterAd(spot1, java.util.List.of(filter1))).thenReturn(List.of(filterAd1));
        adLoadJob.loadNewFilterAds();
        assertThat(filterAdRepository.count()).isEqualTo(1L);
        assertThat(adRepository.count()).isEqualTo(1L);
        final var actual = filterAdRepository.findByFilterIdAndAdId(filter1.getId(), ad1.getId());
        assertThat(actual).isPresent().get().matches(fa ->
                fa.getFilter().equals(filter1) && fa.getAd().equals(ad1) && fa.getAd().getAdBody().equals(adBody1)
        );
    }

    @Test
    void faNotExistsOldAdExists() {
        adRepository.saveAndFlush(ad1);
        filterAd1.getAd().getAdBody().setJsonBody("{\"new_dummy\": \"new_dummy\"}");
        filterAd1.getAd().setHash(2);
        doReturn(List.of(filterAd1)).when(analyzer).findNewFilterAd(spot1, java.util.List.of(filter1));
//        when(analyzer.findNewFilterAd(spot1, java.util.List.of(filter1))).thenReturn(List.of(filterAd1));
        adLoadJob.loadNewFilterAds();
        assertThat(filterAdRepository.count()).isEqualTo(1L);
        assertThat(adRepository.count()).isEqualTo(1L);
        final var actual = filterAdRepository.findByFilterIdAndAdId(filter1.getId(), ad1.getId());
        assertThat(actual).isPresent().get().matches(fa ->
                fa.getFilter().equals(filter1)
                        && fa.getAd().equals(filterAd1.getAd())
                        && fa.getAd().getAdBody().equals(filterAd1.getAd().getAdBody())
        );
    }

    @Test
    void faExistsWithNewAd() {
        ad1 = adRepository.saveAndFlush(ad1);
        filterAd1 = filterAdRepository.saveAndFlush(filterAd1);
        doReturn(List.of(filterAd1)).when(analyzer).findNewFilterAd(spot1, java.util.List.of(filter1));
//        when(analyzer.findNewFilterAd(spot1, java.util.List.of(filter1))).thenReturn(List.of(filterAd1));

        assertThat(filterAdRepository.count()).isEqualTo(1L);
        assertThat(adRepository.count()).isEqualTo(1L);

        adLoadJob.loadNewFilterAds();

        assertThat(filterAdRepository.count()).isEqualTo(1L);
        assertThat(adRepository.count()).isEqualTo(1L);

        final var actual = filterAdRepository.findByFilterIdAndAdId(filter1.getId(), ad1.getId());
        assertThat(actual).isPresent().get().matches(fa ->
                fa.getFilter().equals(filter1)
                        && fa.getAd().equals(filterAd1.getAd())
                        && fa.getAd().getAdBody().equals(filterAd1.getAd().getAdBody())
        );
    }

    @Test
    void faExistsWithOldAd() {
        ad1 = adRepository.saveAndFlush(ad1);
        filterAd1 = filterAdRepository.saveAndFlush(filterAd1);

        assertThat(filterAdRepository.count()).isEqualTo(1L);
        assertThat(adRepository.count()).isEqualTo(1L);

        filterAd1.getAd().getAdBody().setJsonBody("{\"new_dummy\": \"new_dummy\"}");
        filterAd1.getAd().setHash(2);
        doReturn(List.of(filterAd1)).when(analyzer).findNewFilterAd(spot1, java.util.List.of(filter1));
//        when(analyzer.findNewFilterAd(spot1, java.util.List.of(filter1))).thenReturn(List.of(filterAd1));

        adLoadJob.loadNewFilterAds();

        assertThat(filterAdRepository.count()).isEqualTo(1L);
        assertThat(adRepository.count()).isEqualTo(1L);

        final var actual = filterAdRepository.findByFilterIdAndAdId(filter1.getId(), ad1.getId());

        assertThat(actual).isPresent().get()
                .matches(fa -> fa.getFilter().equals(filter1))
                .matches(fa -> fa.getAd().equals(filterAd1.getAd()))
                .matches(fa -> fa.getAd().getAdBody().equals(filterAd1.getAd().getAdBody()));
    }
}
