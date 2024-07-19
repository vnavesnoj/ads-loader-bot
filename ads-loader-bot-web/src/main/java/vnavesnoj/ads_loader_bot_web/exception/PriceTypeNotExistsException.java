package vnavesnoj.ads_loader_bot_web.exception;

import vnavesnoj.ads_loader_bot_common.annotation.BotResponseMessage;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@BotResponseMessage("bot.create.input.price-type.not-exists")
public class PriceTypeNotExistsException extends RuntimeException {

    public PriceTypeNotExistsException() {
        super();
    }

    public PriceTypeNotExistsException(String s) {
        super(s);
    }
}
