package vnavesnoj.ads_loader_bot_service.dto.filter;

import lombok.Value;
import vnavesnoj.ads_loader_bot_common.database.entity.Platform;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class FilterCreateDto {

    String name;

    Platform platform;

    String spot;

    String jsonPattern;

    Long userId;
}
