package vnavesnoj.ads_loader_bot_web.handler;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vnavesnoj.ads_loader_bot_common.annotation.BotResponseMessage;

import java.util.Locale;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class BotExceptionHandlerImpl implements BotExceptionHandler {

    private final MessageSource messageSource;

    @Override
    public BaseRequest<SendMessage, SendResponse> handleException(User user, Chat chat, RuntimeException exception) {
        final Locale locale = Locale.of(user.languageCode());
        final var annotation = Optional.ofNullable(exception.getClass().getAnnotation(BotResponseMessage.class))
                .orElseThrow(() -> exception);
        return Optional.of(annotation)
                .map(BotResponseMessage::value)
                .filter(StringUtils::hasText)
                .map(item -> messageSource.getMessage(item, null, locale))
                .map(item -> new SendMessage(chat.id(), item))
                .orElse(null);
    }
}
