package vnavesnoj.ads_loader_bot_web.handler;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.dto.user.UserReadDto;
import vnavesnoj.ads_loader_bot_service.service.UserService;
import vnavesnoj.ads_loader_bot_web.state.chat.ChatState;
import vnavesnoj.ads_loader_bot_web.state.chat.ChatStateFactory;

import java.util.Locale;
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
    private final MessageSource messageSource;

    @Override
    public BaseRequest<SendMessage, SendResponse> handleRequest(User user, Chat chat, Function<ChatState, BaseRequest<SendMessage, SendResponse>> function) {
        return userService.findById(user.id())
                .map(UserReadDto::getChatState)
                .map(chatStateFactory::getChatStateByName)
                .map(function)
                .orElseGet(() -> this.userNotRegistered(user, chat));
    }


    private BaseRequest<SendMessage, SendResponse> userNotRegistered(User user, Chat chat) {
        final var message = messageSource.getMessage("bot.user-not-registered", null, Locale.of(user.languageCode()));
        return new SendMessage(chat.id(), message);
    }
}
