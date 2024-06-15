package vnavesnoj.ads_loader_bot_service.mapper.spot;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.dto.spot.SpotReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class SpotReadMapper implements Mapper<Spot, SpotReadDto> {

    @Override
    public SpotReadDto map(Spot object) {
        return new SpotReadDto(
                object.getId(),
                object.getPlatform(),
                object.getUrl(),
                object.getName()
        );
    }
}
