package vnavesnoj.ads_loader_bot_web.exception;

import vnavesnoj.ads_loader_bot_common.annotation.BotResponseMessage;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@BotResponseMessage(value = "bot.user-not-registered")
public class UserNotRegisteredException extends RuntimeException {

    public UserNotRegisteredException() {
        super();
    }

    public UserNotRegisteredException(String message) {
        super(message);
    }
}
