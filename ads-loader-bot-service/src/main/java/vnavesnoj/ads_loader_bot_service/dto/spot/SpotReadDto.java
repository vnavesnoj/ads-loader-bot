package vnavesnoj.ads_loader_bot_service.dto.spot;

import lombok.Value;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Platform;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class SpotReadDto {

    Integer id;

    Platform platform;

    String url;

    String name;
}
