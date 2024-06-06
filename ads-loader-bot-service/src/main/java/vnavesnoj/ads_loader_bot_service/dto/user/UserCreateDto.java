package vnavesnoj.ads_loader_bot_service.dto.user;

import lombok.NonNull;
import lombok.Value;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class UserCreateDto {

    @NonNull
    Long id;

    String languageCode;
}
