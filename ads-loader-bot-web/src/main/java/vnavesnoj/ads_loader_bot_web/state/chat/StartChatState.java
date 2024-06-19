package vnavesnoj.ads_loader_bot_web.state.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.UserService;

import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class StartChatState extends BaseChatState {

    private final UserService userService;
    private final FilterBuilderService filterBuilderService;
    private final MessageSource messageSource;

    private final ChatStateEnum name = ChatStateEnum.START;

    public StartChatState(UserService userService,
                          FilterBuilderService
                                  filterBuilderService,
                          MessageSource messageSource) {
        super(userService, filterBuilderService, messageSource);
        this.userService = userService;
        this.filterBuilderService = filterBuilderService;
        this.messageSource = messageSource;
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onCreate(User user, Chat chat) {
        final var locale = Locale.of(user.languageCode());
        if (filterBuilderService.findByUserId(user.id()).isPresent()) {
            final var message = messageSource.getMessage("bot.create.filter-builder-already-exists", null, locale);
            final var keyboard = new InlineKeyboardMarkup()
                    .addRow(new InlineKeyboardButton(
                            messageSource.getMessage("bot.create.button.go-to-builder", null, locale)
                    ).callbackData("/builder"))
                    .addRow(new InlineKeyboardButton(
                            messageSource.getMessage("bot.create.button.create-new-filter", null, locale)
                    ).callbackData("/force-create"));
            return new SendMessage(chat.id(), message)
                    .replyMarkup(keyboard);
        }
        return onForceCreate(user, chat);
    }

    @Override
    public ChatStateEnum getName() {
        return name;
    }
}
