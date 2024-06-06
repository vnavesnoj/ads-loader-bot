package vnavesnoj.ads_loader_bot_service.dto.filter;

import jakarta.annotation.Nullable;
import lombok.Value;
import vnavesnoj.ads_loader_bot_service.dto.spot.SpotReadDto;

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

    SpotReadDto spotReadDto;

    boolean enabled;

    Long userId;
}
