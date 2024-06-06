package vnavesnoj.ads_loader_bot_service.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Ad;
import vnavesnoj.ads_loader_bot_service.dto.AdReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class AdReadMapper implements Mapper<Ad, AdReadDto> {

    @Override
    public AdReadDto map(Ad object) {
        return new AdReadDto(
                object.getId(),
                object.getPlatform(),
                object.getUrl(),
                object.getTitle(),
                object.getPushupTime(),
                object.getAdBody().getJsonBody()
        );
    }
}
