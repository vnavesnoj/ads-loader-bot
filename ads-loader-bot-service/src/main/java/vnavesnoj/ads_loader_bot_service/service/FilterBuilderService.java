package vnavesnoj.ads_loader_bot_service.service;

import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderService {

    Optional<FilterBuilderReadDto> findById(Long id);

    Optional<FilterBuilderReadDto> findByUserId(Long userId);

    boolean deleteByUserId(Long userId);
}
