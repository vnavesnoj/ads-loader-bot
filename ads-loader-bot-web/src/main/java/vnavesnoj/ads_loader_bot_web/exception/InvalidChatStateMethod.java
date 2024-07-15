package vnavesnoj.ads_loader_bot_web.exception;

import lombok.NoArgsConstructor;
import vnavesnoj.ads_loader_bot_common.annotation.BotResponseMessage;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@NoArgsConstructor
@BotResponseMessage
public class InvalidChatStateMethod extends RuntimeException {

    public InvalidChatStateMethod(String message) {
        super(message);
    }
}
