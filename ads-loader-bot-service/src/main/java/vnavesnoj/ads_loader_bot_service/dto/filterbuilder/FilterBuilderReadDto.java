package vnavesnoj.ads_loader_bot_service.dto.filterbuilder;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import vnavesnoj.ads_loader_bot_service.dto.spot.SpotReadDto;
import vnavesnoj.ads_loader_bot_service.dto.user.UserReadDto;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@FieldNameConstants
@Value
public class FilterBuilderReadDto {

    @NonNull
    Long id;

    @NonNull
    Instant instant;

    String pattern;

    String currentInput;

    @NonNull
    SpotReadDto spot;

    @NonNull
    UserReadDto user;
}
