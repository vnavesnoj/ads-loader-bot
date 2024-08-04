package vnavesnoj.ads_loader_bot_web.assistant.component;

import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderManager {

    FilterBuilderReadDto create(Integer spotId, Long userId);

    String updateField(Long filterBuilderId, String field, String value);

    String resetField(Long filterBuilderId, String field);
}
