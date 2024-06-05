package vnavesnoj.ads_loader_bot_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vnavesnoj.ads_loader_bot_service.dto.FilterAdReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterAdService {

    Page<FilterAdReadDto> findAllByFilterId(Long id, Pageable pageable);

    Page<FilterAdReadDto> findAllByUserId(Long id, Pageable pageable);

    Page<FilterAdReadDto> findAllByFilterNameAndUserId(String name, Long id, Pageable pageable);
}
