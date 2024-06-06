package vnavesnoj.ads_loader_bot_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterEditDto;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterMetaReadDto;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterReadDto;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterService {

    Page<FilterMetaReadDto> findAllByUserId(Long id, Pageable pageable);

    Optional<FilterReadDto> findById(Long id);

    Optional<FilterReadDto> findByNameAndUserId(String name, Long userId);

    FilterReadDto create(FilterCreateDto filter);

    Optional<FilterReadDto> patch(Long id, FilterEditDto filter);

    boolean delete(Long id);

    boolean deleteByNameAndUserId(String name, Long userId);
}
