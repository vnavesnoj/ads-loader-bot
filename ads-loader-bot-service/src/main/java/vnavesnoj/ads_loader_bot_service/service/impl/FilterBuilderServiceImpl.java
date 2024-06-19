package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterBuilder;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterBuilderRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Service
public class FilterBuilderServiceImpl implements FilterBuilderService {

    private final FilterBuilderRepository filterBuilderRepository;

    private final Mapper<FilterBuilder, FilterBuilderReadDto> filterBuilderReadMapper;

    @Override
    public Optional<FilterBuilderReadDto> findById(Long id) {
        return filterBuilderRepository.findById(id)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    public Optional<FilterBuilderReadDto> findByUserId(Long userId) {
        return filterBuilderRepository.findByUserId(userId)
                .map(filterBuilderReadMapper::map);
    }

    @Override
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
