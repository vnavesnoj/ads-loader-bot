package vnavesnoj.ads_loader_bot_service.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.dto.FilterMetaReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class FilterMetaReadMapper implements Mapper<Filter, FilterMetaReadDto> {

    @Override
    public FilterMetaReadDto map(Filter object) {
        return new FilterMetaReadDto(
                object.getId(),
                object.getName(),
                object.getDescription(),
                object.getInstant(),
                object.getPlatform(),
                object.getSpot(),
                object.isEnabled(),
                object.getUser().getId()
        );
    }
}
