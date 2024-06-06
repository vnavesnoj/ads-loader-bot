package vnavesnoj.ads_loader_bot_service.dto.user;

import lombok.Value;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class UserReadDto {

    Long id;

    String languageCode;

    Instant instant;

    boolean notify;
}
