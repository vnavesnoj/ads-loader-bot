package vnavesnoj.ads_loader_bot_service.service;

import vnavesnoj.ads_loader_bot_service.dto.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderService {

    Optional<FilterBuilderReadDto> findById(Long id);

    Optional<FilterBuilderReadDto> findByUserId(Long userId);

    Optional<FilterBuilderReadDto> findByIdAndUserId(Long id, Long userId);

    FilterBuilderReadDto create(FilterBuilderCreateDto filterBuilder);

    Optional<FilterBuilderReadDto> updateCurrentInput(Long id, String input);

    boolean deleteByUserId(Long userId);
}
