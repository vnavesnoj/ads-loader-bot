package vnavesnoj.ads_loader_bot_web.exception;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.MessageSource;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;

import javax.validation.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Getter
public class OlxDefaultPatternValidationException extends ValidationException implements BotResponseMessageException {

    private final Set<ConstraintViolation<OlxDefaultPattern>> errors;

    public OlxDefaultPatternValidationException(@NonNull Set<ConstraintViolation<OlxDefaultPattern>> errors) {
        super();
        this.errors = errors;
    }

    public OlxDefaultPatternValidationException(String s,
                                                @NonNull Set<ConstraintViolation<OlxDefaultPattern>> errors) {
        super(s);
        this.errors = errors;
    }

    public OlxDefaultPatternValidationException(String s,
                                                Throwable throwable,
                                                @NonNull Set<ConstraintViolation<OlxDefaultPattern>> errors) {
        super(s, throwable);
        this.errors = errors;
    }

    public OlxDefaultPatternValidationException(Throwable throwable,
                                                @NonNull Set<ConstraintViolation<OlxDefaultPattern>> errors) {
        super(throwable);
        this.errors = errors;
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> getResponseMessage(User user, Chat chat, MessageSource messageSource) {
        final var message = errors.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
        return new SendMessage(chat.id(), message);
    }
}
