package vnavesnoj.ads_loader_bot_service.service;

import org.springframework.data.domain.Page;
import vnavesnoj.ads_loader_bot_service.dto.FilterCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.FilterEditDto;
import vnavesnoj.ads_loader_bot_service.dto.FilterMetaReadDto;
import vnavesnoj.ads_loader_bot_service.dto.FilterReadDto;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterService {

    Page<FilterMetaReadDto> findAllByUserId(Long id);

    Optional<FilterReadDto> findById(Long id);

    Optional<FilterReadDto> findByNameAndUserId(String name, Long userId);

    FilterReadDto create(FilterCreateDto filter);

    Optional<FilterReadDto> update(Long id, FilterEditDto filter);

    boolean delete(Long id);

    boolean deleteByNameAndUserId(String name, Long userId);
}
