package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterAd;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterAdRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterad.FilterAdReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.FilterAdService;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Service
public class FilterAdServiceImpl implements FilterAdService {

    private final FilterAdRepository filterAdRepository;

    private final Mapper<FilterAd, FilterAdReadDto> filterAdReadMapper;

    @Override
    public Page<FilterAdReadDto> findAllByFilterId(Long id, Pageable pageable) {
        return filterAdRepository.findAllByFilterId(id, pageable)
                .map(filterAdReadMapper::map);
    }

    @Override
    public Page<FilterAdReadDto> findAllByUserId(Long id, Pageable pageable) {
        return filterAdRepository.findAllByUserId(id, pageable)
                .map(filterAdReadMapper::map);
    }

    @Override
    public Page<FilterAdReadDto> findAllByFilterNameAndUserId(String name, Long id, Pageable pageable) {
        return filterAdRepository.findAllByFilterNameAndUserId(name, id, pageable)
                .map(filterAdReadMapper::map);
    }
}
