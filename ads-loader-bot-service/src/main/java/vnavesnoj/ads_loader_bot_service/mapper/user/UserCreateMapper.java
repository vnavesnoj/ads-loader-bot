package vnavesnoj.ads_loader_bot_service.mapper.user;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_persistence.database.entity.User;
import vnavesnoj.ads_loader_bot_service.dto.user.UserCreateDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    @Override
    public User map(UserCreateDto object) {
        return User.builder()
                .id(object.getId())
                .languageCode(object.getLanguageCode())
                .chatState(object.getChatState())
                .build();
    }
}
