package vnavesnoj.ads_loader_bot_web.assistant.component;

import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderManager<T> {

    FilterBuilderReadDto createNewFilterBuilder(Long userId, Integer spotId);

    Optional<FilterBuilderReadDto> updatePatternField(FilterBuilderReadDto filterBuilder, String value);

    Optional<FilterBuilderReadDto> updatePatternField(Long filterBuilderId, String patternField, String value);

    Optional<FilterBuilderReadDto> resetPatternField(FilterBuilderReadDto filterBuilder, String field);
}
