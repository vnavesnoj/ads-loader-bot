package vnavesnoj.ads_loader_bot_web.state.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.UserService;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
public abstract class BaseChatState implements ChatState {

    private final UserService userService;
    private final FilterBuilderService filterBuilderService;
    private final MessageSource messageSource;

    @Override
    public BaseRequest<SendMessage, SendResponse> onForceCreate(User user, Chat chat) {
        filterBuilderService.deleteByUserId(user.id());
        final var locale = Locale.of(user.languageCode());
        final var buttons = Arrays.stream(Platform.values())
                .map(item -> new InlineKeyboardButton(item.getDomain()).callbackData(item.getDomain()))
                .toArray(InlineKeyboardButton[]::new);
        final var keybord = new InlineKeyboardMarkup(buttons);
        final var message = messageSource.getMessage("bot.create.choose-platform", null, locale);
        final var response = new SendMessage(chat.id(), message)
                .replyMarkup(keybord);
        userService.updateChatState(user.id(), ChatStateEnum.BUILDER_START);
        return response;
    }
}
