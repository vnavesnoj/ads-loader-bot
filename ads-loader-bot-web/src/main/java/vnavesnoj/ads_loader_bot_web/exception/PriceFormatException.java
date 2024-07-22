package vnavesnoj.ads_loader_bot_web.exception;

import vnavesnoj.ads_loader_bot_common.annotation.BotResponseMessage;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@BotResponseMessage("bot.create.input.illegal-price-format")
public class PriceFormatException extends RuntimeException {

    public PriceFormatException() {
        super();
    }

    public PriceFormatException(String s) {
        super(s);
    }

    public PriceFormatException(RuntimeException e) {
        super(e);
    }

    public PriceFormatException(String s, RuntimeException e) {
        super(s, e);
    }
}
