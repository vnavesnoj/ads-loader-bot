package vnavesnoj.ads_loader_bot_service.service;

import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderEditDto;
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

    Optional<FilterBuilderReadDto> updatePattern(Long id, String pattern);

    Optional<FilterBuilderReadDto> updatePatternField(Long id, Object value);

    Optional<FilterBuilderReadDto> updatePatternField(Long id, String fieldName, Object value);

    Optional<FilterBuilderReadDto> update(Long id, FilterBuilderEditDto filterBuilder);

    boolean deleteByUserId(Long userId);
}
