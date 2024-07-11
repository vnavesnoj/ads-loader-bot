package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterBuilder;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterBuilderRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderEditDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FilterBuilderServiceImpl implements FilterBuilderService {

    private final FilterBuilderRepository filterBuilderRepository;

    private final Mapper<FilterBuilder, FilterBuilderReadDto> filterBuilderReadMapper;
    private final Mapper<FilterBuilderCreateDto, FilterBuilder> filterBuilderCreateMapper;
    private final Mapper<FilterBuilderEditDto, FilterBuilder> filterBuilderEditMapper;

    @Override
    public Optional<FilterBuilderReadDto> findById(Long id) {
        return filterBuilderRepository.findById(id)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    public Optional<FilterBuilderReadDto> findByIdAndUserId(Long id, Long userId) {
        return filterBuilderRepository.findByIdAndUserId(id, userId)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    @Transactional
    public FilterBuilderReadDto create(FilterBuilderCreateDto filterBuilder) {
        return Optional.ofNullable(filterBuilder)
                .map(filterBuilderCreateMapper::map)
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map)
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public Optional<FilterBuilderReadDto> updateCurrentInput(Long id, String input) {
        return filterBuilderRepository.findById(id)
                .map(item -> {
                    item.setCurrentInput(input);
                    return item;
                })
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    public Optional<FilterBuilderReadDto> updatePattern(Long id, String pattern) {
        return filterBuilderRepository.findById(id)
                .map(item -> {
                    item.setPattern(pattern);
                    return item;
                })
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    @Transactional
    public Optional<FilterBuilderReadDto> update(Long id, FilterBuilderEditDto filterBuilder) {
        return filterBuilderRepository.findById(id)
                .map(item -> filterBuilderEditMapper.map(filterBuilder, item))
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    public Optional<FilterBuilderReadDto> findByUserId(Long userId) {
        return filterBuilderRepository.findByUserId(userId)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    @Transactional
    public boolean deleteByUserId(Long userId) {
        return filterBuilderRepository.findByUserId(userId)
                .map(item -> {
                    filterBuilderRepository.delete(item);
                    filterBuilderRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
