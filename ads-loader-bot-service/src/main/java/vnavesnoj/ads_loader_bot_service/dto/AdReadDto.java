package vnavesnoj.ads_loader_bot_service.dto;

import lombok.Value;
import vnavesnoj.ads_loader_bot_common.database.entity.Platform;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class AdReadDto {

    Long id;

    Platform platform;

    String url;

    String title;

    Instant pushupTime;

    String jsonBody;
}
