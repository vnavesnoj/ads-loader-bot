package vnavesnoj.ads_loader_bot_web.handler;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vnavesnoj.ads_loader_bot_common.annotation.BotResponseMessage;
import vnavesnoj.ads_loader_bot_service.exception.PatternValidationException;
import vnavesnoj.ads_loader_bot_web.exception.BotResponseMessageException;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (exception instanceof BotResponseMessageException) {
            return ((BotResponseMessageException) exception).getResponseMessage(user, chat, messageSource);
        }
        if (exception instanceof PatternValidationException) {
            final var errors = ((PatternValidationException) exception).getErrors();
            return getMessageFromValidationErrors(chat.id(), errors);
        }
        return getMessageFromAnnotation(chat, exception, LocaleContextHolder.getLocale());
    }

    private BaseRequest<SendMessage, SendResponse> getMessageFromValidationErrors(Long id, Set<ConstraintViolation<Object>> errors) {
        final var message = errors.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
        return new SendMessage(id, message);
    }

    @Nullable
    private BaseRequest<SendMessage, SendResponse> getMessageFromAnnotation(Chat chat, RuntimeException exception, Locale locale) {
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
