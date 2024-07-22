package vnavesnoj.ads_loader_bot_web.exception;

import vnavesnoj.ads_loader_bot_common.annotation.BotResponseMessage;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@BotResponseMessage("bot.create.input.currency-code.not-exists")
public class CurrencyCodeNotExistsException extends RuntimeException {

    public CurrencyCodeNotExistsException() {
        super();
    }

    public CurrencyCodeNotExistsException(String s) {
        super(s);
    }
}
