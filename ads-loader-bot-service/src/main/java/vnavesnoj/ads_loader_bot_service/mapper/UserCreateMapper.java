package vnavesnoj.ads_loader_bot_service.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.User;
import vnavesnoj.ads_loader_bot_service.dto.UserCreateDto;

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
                .build();
    }
}
