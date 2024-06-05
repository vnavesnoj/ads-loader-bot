package vnavesnoj.ads_loader_bot_service.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.User;
import vnavesnoj.ads_loader_bot_service.dto.UserEditDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class UserEditMapper implements Mapper<UserEditDto, User> {

    @Override
    public User map(UserEditDto object) {
        final var user = new User();
        copy(object, user);
        return null;
    }

    @Override
    public User map(UserEditDto from, User to) {
        copy(from, to);
        return to;
    }

    private void copy(UserEditDto from, User to) {
        if (from.getLanguageCode() != null) {
            to.setLanguageCode(from.getLanguageCode());
        }
        if (from.getNotify() != null) {
            to.setNotify(from.getNotify());
        }
    }
}
