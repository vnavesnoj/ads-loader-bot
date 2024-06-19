package vnavesnoj.ads_loader_bot_service.mapper.filterbuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterBuilder;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
import vnavesnoj.ads_loader_bot_persistence.database.entity.User;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.dto.spot.SpotReadDto;
import vnavesnoj.ads_loader_bot_service.dto.user.UserReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class FilterBuilderReadMapper implements Mapper<FilterBuilder, FilterBuilderReadDto> {

    private final Mapper<Spot, SpotReadDto> spotReadMapper;
    private final Mapper<User, UserReadDto> userReadMapper;

    @Override
    public FilterBuilderReadDto map(FilterBuilder object) {
        return new FilterBuilderReadDto(
                object.getId(),
                object.getInstant(),
                object.getPattern(),
                object.getCurrentInput(),
                spotReadMapper.map(object.getSpot()),
                userReadMapper.map(object.getUser())
        );
    }
}
