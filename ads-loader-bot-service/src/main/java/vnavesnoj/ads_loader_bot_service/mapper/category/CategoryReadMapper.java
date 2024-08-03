package vnavesnoj.ads_loader_bot_service.mapper.category;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.mapper.Mapper;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Category;
import vnavesnoj.ads_loader_bot_service.dto.category.CategoryReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class CategoryReadMapper implements Mapper<Category, CategoryReadDto> {

    @Override
    public CategoryReadDto map(Category object) {
        return new CategoryReadDto(
                object.getId(),
                object.getPlatform(),
                object.getName()
        );
    }
}
