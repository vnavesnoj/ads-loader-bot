package vnavesnoj.ads_loader_bot_service.mapper.user;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_persistence.database.entity.User;
import vnavesnoj.ads_loader_bot_service.dto.user.UserReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

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
                object.isNotify(),
                object.getChatState()
        );
    }
}
