package vnavesnoj.ads_loader_bot_service.mapper.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_common.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterMetaReadDto;
import vnavesnoj.ads_loader_bot_service.dto.spot.SpotReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class FilterMetaReadMapper implements Mapper<Filter, FilterMetaReadDto> {

    private final Mapper<Spot, SpotReadDto> spotReadMapper;

    @Override
    public FilterMetaReadDto map(Filter object) {
        return new FilterMetaReadDto(
                object.getId(),
                object.getName(),
                object.getDescription(),
                object.getInstant(),
                spotReadMapper.map(object.getSpot()),
                object.isEnabled(),
                object.getUser().getId()
        );
    }
}
