package vnavesnoj.ads_loader_bot_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vnavesnoj.ads_loader_bot_service.dto.spot.SpotReadDto;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface SpotService {

    Page<SpotReadDto> findAllByCategoryId(Integer categoryId, Pageable pageable);

    Optional<SpotReadDto> findById(Integer id);
}
