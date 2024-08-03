package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnavesnoj.ads_loader_bot_common.mapper.Mapper;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Ad;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Filter;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterAd;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.database.repository.AdRepository;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterAdRepository;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterRepository;
import vnavesnoj.ads_loader_bot_service.factory.AnalyzerFactory;
import vnavesnoj.ads_loader_bot_service.service.AdLoadJob;
import vnavesnoj.ads_loader_bot_service.service.component.AdAnalyzer;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(value = "app.ad-load-job.enabled", matchIfMissing = true)
public class AdLoadJobImpl implements AdLoadJob {

    private final FilterRepository filterRepository;
    private final FilterAdRepository filterAdRepository;
    private final AdRepository adRepository;
    private final AnalyzerFactory analyzerFactory;

    private final Mapper<Ad, Ad> adCopyMapper;

    private final Pageable defaultPageable = PageRequest.of(0, 50, Sort.by(Filter.Fields.spot));
    private Pageable currentPageable = defaultPageable;

    @Transactional
    @Scheduled(fixedDelay = 2000)
    @Override
    public void loadNewFilterAds() {
        if (currentPageable.isUnpaged()) {
            currentPageable = defaultPageable;
        }
        final var filters = filterRepository.findAll(currentPageable);
        if (!filters.isEmpty()) {
            loadAndSaveNewFilterAds(filters);
            currentPageable = filters.nextPageable();
        } else {
            currentPageable = defaultPageable;
        }
    }

    private void loadAndSaveNewFilterAds(Page<Filter> filters) {
        final var groupedFilters = filters.stream()
                .collect(Collectors.groupingBy(Filter::getSpot));
        for (Spot spot : groupedFilters.keySet()) {
            AdAnalyzer analyzer = analyzerFactory.getAdAnalyzer(spot.getAnalyzer());
            final var newFilterAds = analyzer.findNewFilterAd(spot, groupedFilters.get(spot));
            for (FilterAd newFilterAd : newFilterAds) {
                saveOrUpdate(newFilterAd);
            }
        }
    }

    private void saveOrUpdate(FilterAd newFilterAd) {
        filterAdRepository
                .findByFilterIdAndAdId(newFilterAd.getFilter().getId(), newFilterAd.getAd().getId())
                .ifPresentOrElse(
                        item -> updateAd(item.getAd(), newFilterAd.getAd()),
                        () -> adRepository.findById(newFilterAd.getAd().getId())
                                .map(entity -> updateAd(entity, newFilterAd.getAd()))
                                .map(ad -> {
                                    newFilterAd.setAd(ad);
                                    return newFilterAd;
                                })
                                .map(filterAdRepository::saveAndFlush)
                                .orElseGet(() -> {
                                    adRepository.save(newFilterAd.getAd());
                                    return filterAdRepository.saveAndFlush(newFilterAd);
                                })
                );
    }

    private Ad updateAd(@NonNull Ad oldAd, @NonNull Ad newAd) {
        return Optional.of(oldAd)
                .filter(item -> item.getHash() != newAd.getHash())
                .map(item -> adCopyMapper.map(newAd, item))
                .map(adRepository::save)
                .orElse(oldAd);
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void resetCycle() {
        currentPageable = defaultPageable;
    }
}
