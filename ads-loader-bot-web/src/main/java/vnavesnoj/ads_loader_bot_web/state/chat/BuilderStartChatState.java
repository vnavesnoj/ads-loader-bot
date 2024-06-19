package vnavesnoj.ads_loader_bot_web.state.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.UserService;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class BuilderStartChatState extends BaseChatState {

    private final UserService userService;
    private final FilterBuilderService filterBuilderService;
    private final MessageSource messageSource;

    public BuilderStartChatState(UserService userService,
                                 FilterBuilderService filterBuilderService,
                                 MessageSource messageSource) {
        super(userService, filterBuilderService, messageSource);
        this.userService = userService;
        this.filterBuilderService = filterBuilderService;
        this.messageSource = messageSource;
    }

    //TODO
    @Override
    public BaseRequest<SendMessage, SendResponse> onCreate(User user, Chat chat) {
        return null;
    }

    @Override
    public ChatStateEnum getName() {
        return ChatStateEnum.BUILDER_START;
    }
}
