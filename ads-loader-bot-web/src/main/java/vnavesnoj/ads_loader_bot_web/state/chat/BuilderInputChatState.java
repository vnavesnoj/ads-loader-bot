package vnavesnoj.ads_loader_bot_web.state.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_service.service.CategoryService;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.SpotService;
import vnavesnoj.ads_loader_bot_service.service.UserService;
import vnavesnoj.ads_loader_bot_web.exception.FilterBuilderNotFoundException;
import vnavesnoj.ads_loader_bot_web.factory.builderassistant.FilterBuilderAssistantFactory;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class BuilderInputChatState extends BaseChatState {

    private final FilterBuilderService filterBuilderService;
    private final FilterBuilderAssistantFactory filterBuilderAssistantFactory;

    private final ChatStateEnum chatStateName = ChatStateEnum.BUILDER_INPUT;

    public BuilderInputChatState(UserService userService,
                                 FilterBuilderService filterBuilderService,
                                 CategoryService categoryService,
                                 SpotService spotService,
                                 FilterBuilderAssistantFactory filterBuilderAssistantFactory,
                                 MessageSource messageSource) {
        super(userService, filterBuilderService, categoryService, spotService, filterBuilderAssistantFactory, messageSource);
        this.filterBuilderService = filterBuilderService;
        this.filterBuilderAssistantFactory = filterBuilderAssistantFactory;
    }

    @Override
    public ChatStateEnum getName() {
        return chatStateName;
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onInput(User user, Chat chat, String input) {
        filterBuilderService.findByUserId(user.id())
                .map(item -> filterBuilderAssistantFactory.getAssistant(item.getSpot().getAnalyzer())
                        .handleInputRequest(item, input))
                .orElseThrow(() -> new FilterBuilderNotFoundException("FilterBuilder with user.id = " + user.id() + " not exists"));
        return onBuilder(user, chat);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onChooseResetInput(User user, Chat chat, Long filterBuilderId, String input) {
        filterBuilderService.findByIdAndUserId(filterBuilderId, user.id())
                .map(item -> filterBuilderAssistantFactory.getAssistant(item.getSpot().getAnalyzer())
                        .resetInput(item, input))
                .orElseThrow(() -> new FilterBuilderNotFoundException("FilterBuilder with id = " + filterBuilderId
                        + " and FilterBuilder.user.id = " + user.id()
                        + " does not exist"));
        return onBuilder(user, chat);
    }
}
