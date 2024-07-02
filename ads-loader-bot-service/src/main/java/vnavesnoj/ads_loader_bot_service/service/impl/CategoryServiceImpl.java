package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Category;
import vnavesnoj.ads_loader_bot_service.database.repository.CategoryRepository;
import vnavesnoj.ads_loader_bot_service.dto.category.CategoryReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.CategoryService;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper<Category, CategoryReadDto> categoryReadMapper;

    @Override
    public Page<CategoryReadDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryReadMapper::map);
    }

    @Override
    public Page<CategoryReadDto> findAllByPlatform(Platform platform, Pageable pageable) {
        return categoryRepository.findAllByPlatform(platform, pageable)
                .map(categoryReadMapper::map);
    }

    @Override
    public Optional<CategoryReadDto> findById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryReadMapper::map);
    }
}
