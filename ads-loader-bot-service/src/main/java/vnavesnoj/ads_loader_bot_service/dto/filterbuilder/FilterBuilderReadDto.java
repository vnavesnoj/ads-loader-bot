package vnavesnoj.ads_loader_bot_service.dto.filterbuilder;

import lombok.Value;
import vnavesnoj.ads_loader_bot_service.dto.spot.SpotReadDto;
import vnavesnoj.ads_loader_bot_service.dto.user.UserReadDto;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class FilterBuilderReadDto {

    Long id;

    Instant instant;

    String pattern;

    String currentInput;

    SpotReadDto spot;

    UserReadDto user;
}
