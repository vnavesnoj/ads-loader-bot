package vnavesnoj.ads_loader_bot_service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.dto.FilterMetaReadDto;
import vnavesnoj.ads_loader_bot_service.dto.FilterReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class FilterReadMapper implements Mapper<Filter, FilterReadDto> {

    private final Mapper<Filter, FilterMetaReadDto> filterMetaReadMapper;

    @Override
    public FilterReadDto map(Filter object) {
        return new FilterReadDto(
                filterMetaReadMapper.map(object),
                object.getJsonPattern()
        );
    }
}
