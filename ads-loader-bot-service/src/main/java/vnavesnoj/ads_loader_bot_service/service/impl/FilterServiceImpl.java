package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterRepository;
import vnavesnoj.ads_loader_bot_service.dto.FilterCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.FilterEditDto;
import vnavesnoj.ads_loader_bot_service.dto.FilterMetaReadDto;
import vnavesnoj.ads_loader_bot_service.dto.FilterReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.FilterService;

import java.time.Instant;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Service
public class FilterServiceImpl implements FilterService {

    private final FilterRepository filterRepository;

    private final Mapper<Filter, FilterReadDto> filterReadMapper;
    private final Mapper<Filter, FilterMetaReadDto> filterMetaReadMapper;
    private final Mapper<FilterCreateDto, Filter> filterCreateMapper;
    private final Mapper<FilterEditDto, Filter> filterEditMapper;

    @Override
    public Page<FilterMetaReadDto> findAllByUserId(Long id, Pageable pageable) {

        return filterRepository.findAllByUserId(id, pageable)
                .map(filterMetaReadMapper::map);
    }

    @Override
    public Optional<FilterReadDto> findById(Long id) {
        return filterRepository.findById(id)
                .map(filterReadMapper::map);
    }

    @Override
    public Optional<FilterReadDto> findByNameAndUserId(String name, Long userId) {
        return filterRepository.findByNameAndUserId(name, userId)
                .map(filterReadMapper::map);
    }

    @Override
    public FilterReadDto create(@NonNull FilterCreateDto filter) {
        return Optional.of(filter)
                .map(filterCreateMapper::map)
                .map(entity -> {
                    entity.setInstant(Instant.now());
                    entity.setEnabled(true);
                    return entity;
                })
                .map(filterRepository::saveAndFlush)
                .map(filterReadMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<FilterReadDto> patch(Long id, FilterEditDto filter) {
        return filterRepository.findById(id)
                .map(entity -> filterEditMapper.map(filter, entity))
                .map(filterRepository::saveAndFlush)
                .map(filterReadMapper::map);
    }

    @Override
    public boolean delete(Long id) {
        return filterRepository.findById(id)
                .map(entity -> {
                    filterRepository.delete(entity);
                    filterRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteByNameAndUserId(String name, Long userId) {
        return filterRepository.findByNameAndUserId(name, userId)
                .map(entity -> {
                    filterRepository.delete(entity);
                    filterRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}