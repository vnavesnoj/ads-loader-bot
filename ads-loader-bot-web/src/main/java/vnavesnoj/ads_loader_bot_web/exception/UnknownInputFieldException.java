package vnavesnoj.ads_loader_bot_web.exception;

import vnavesnoj.ads_loader_bot_common.annotation.BotResponseMessage;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@BotResponseMessage
public class UnknownInputFieldException extends RuntimeException {
    public UnknownInputFieldException(String s) {
        super(s);
    }
}
