package vnavesnoj.ads_loader_bot_web.handler;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.dto.user.UserReadDto;
import vnavesnoj.ads_loader_bot_service.service.UserService;
import vnavesnoj.ads_loader_bot_web.exception.UserNotRegisteredException;
import vnavesnoj.ads_loader_bot_web.state.chat.ChatState;
import vnavesnoj.ads_loader_bot_web.state.chat.ChatStateFactory;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class TgFilterBotRequestHandler implements BotRequestHandler {

    private final UserService userService;
    private final ChatStateFactory chatStateFactory;
    private final BotExceptionHandler botExceptionHandler;

    @Override
    public BaseRequest<SendMessage, SendResponse> handleRequest(User user, Chat chat, Function<ChatState, BaseRequest<SendMessage, SendResponse>> function) {
        try {
            final var actualUser = userService.findById(user.id())
                    .orElseThrow(UserNotRegisteredException::new);
            Optional.ofNullable(actualUser.getLanguageCode())
                    .map(Locale::of)
                    .ifPresent(LocaleContextHolder::setLocale);
            return Optional.of(actualUser)
                    .map(UserReadDto::getChatState)
                    .map(chatStateFactory::getChatStateByName)
                    .map(function)
                    .orElse(null);
        } catch (RuntimeException e) {
            return botExceptionHandler.handleException(user, chat, e);
        }
    }
}
