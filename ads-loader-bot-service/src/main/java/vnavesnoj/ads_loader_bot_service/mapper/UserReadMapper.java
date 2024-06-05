package vnavesnoj.ads_loader_bot_service.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.User;
import vnavesnoj.ads_loader_bot_service.dto.UserReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getLanguageCode(),
                object.getInstant(),
                object.isNotify()
        );
    }
}
