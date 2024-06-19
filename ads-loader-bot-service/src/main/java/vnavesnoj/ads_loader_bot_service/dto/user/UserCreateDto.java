package vnavesnoj.ads_loader_bot_service.dto.user;

import lombok.NonNull;
import lombok.Value;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class UserCreateDto {

    @NonNull
    Long id;

    String languageCode;

    ChatStateEnum chatState;
}
