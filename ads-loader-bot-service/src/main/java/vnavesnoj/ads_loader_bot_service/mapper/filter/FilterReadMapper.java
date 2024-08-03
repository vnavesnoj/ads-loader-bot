package vnavesnoj.ads_loader_bot_service.mapper.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.mapper.Mapper;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterMetaReadDto;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterReadDto;

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
