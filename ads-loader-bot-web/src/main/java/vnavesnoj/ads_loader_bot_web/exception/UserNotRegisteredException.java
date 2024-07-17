package vnavesnoj.ads_loader_bot_web.exception;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public class UserNotRegisteredException extends RuntimeException implements BotResponseMessageException {

    public UserNotRegisteredException() {
        super();
    }

    public UserNotRegisteredException(String message) {
        super(message);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> getResponseMessage(User user, Chat chat, MessageSource messageSource) {
        final var locale = Optional.ofNullable(user.languageCode())
                .map(Locale::of)
                .orElseGet(LocaleContextHolder::getLocale);
        final var message = messageSource.getMessage("bot.user-not-registered", null, locale);
        return new SendMessage(chat.id(), message);
    }
}
