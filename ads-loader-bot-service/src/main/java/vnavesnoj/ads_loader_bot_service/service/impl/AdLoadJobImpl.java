package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnavesnoj.ads_loader_bot_common.database.entity.*;
import vnavesnoj.ads_loader_bot_service.database.repository.AdRepository;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterAdRepository;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterRepository;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
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
@ConditionalOnProperty(value = "app.ad-load-job.enable", matchIfMissing = true)
public class AdLoadJobImpl implements AdLoadJob {

    private final FilterRepository filterRepository;
    private final FilterAdRepository filterAdRepository;
    private final AdRepository adRepository;
    private final AdAnalyzer olxDefaultAnalyzer;

    private final Mapper<FilterAd, FilterAd> filterAdCopyMapper;
    private final Mapper<Ad, Ad> adCopyMapper;

    @Transactional
    @Override
    public void loadNewFilterAds() {
        Pageable pageRequest = PageRequest.of(0, 50, Sort.by(Filter.Fields.spot));
        while (pageRequest.isPaged()) {
            final var filters = filterRepository.findAll(pageRequest);
            if (filters.isEmpty()) {
                break;
            }
            final var groupedFilters = filters.stream()
                    .collect(Collectors.groupingBy(Filter::getSpot));
            for (Spot spot : groupedFilters.keySet()) {
                AdAnalyzer analyzer;
                if (spot.getAnalyzer().equals(Analyzer.OLX_DEFAULT)) {
                    analyzer = olxDefaultAnalyzer;
                } else {
                    throw new RuntimeException();
                }
                final var newFilterAds = analyzer.findNewFilterAd(spot, groupedFilters.get(spot));
                for (FilterAd newFilterAd : newFilterAds) {
                    filterAdRepository
                            .findByFilterIdAndAdId(newFilterAd.getFilter().getId(), newFilterAd.getAd().getId())
                            .ifPresentOrElse(
                                    item -> Optional.of(item)
                                            .filter(entity -> entity.getAd().getHash() != newFilterAd.getAd().getHash())
                                            .map(entity -> filterAdCopyMapper.map(newFilterAd, entity))
                                            .map(entity -> adRepository.saveAndFlush(entity.getAd())),
                                    () -> adRepository.findById(newFilterAd.getAd().getId())
                                            .map(entity ->
                                                    Optional.of(entity)
                                                            .filter(item -> item.getHash() != newFilterAd.getAd().getHash())
                                                            .map(item -> adCopyMapper.map(newFilterAd.getAd(), entity))
                                                            .map(adRepository::save)
                                                            .orElse(entity)
                                            )
                                            .map(ad -> {
                                                newFilterAd.setAd(ad);
                                                return newFilterAd;
                                            })
                                            .ifPresentOrElse(
                                                    filterAdRepository::saveAndFlush,
                                                    () -> {
                                                        System.out.println(newFilterAd.getAd());
                                                        adRepository.save(newFilterAd.getAd());
                                                        filterAdRepository.saveAndFlush(newFilterAd);
                                                        adRepository.flush();
                                                    })
                            );
                }
            }
            pageRequest = filters.nextPageable();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }
}
