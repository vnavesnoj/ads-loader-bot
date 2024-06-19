package vnavesnoj.ads_loader_bot_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_service.dto.category.CategoryReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface CategoryService {

    Page<CategoryReadDto> findAll(Pageable pageable);

    Page<CategoryReadDto> findAllByPlatform(Platform platform, Pageable pageable);
}
