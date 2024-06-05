package vnavesnoj.ads_loader_bot_service.dto;

import lombok.Value;
import org.springframework.lang.Nullable;
import vnavesnoj.ads_loader_bot_common.database.entity.Platform;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class FilterMetaReadDto {

    Long id;

    String name;

    @Nullable
    String description;

    Instant instant;

    Platform platform;

    String spot;

    boolean enabled;

    Long userId;
}
