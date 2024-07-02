package vnavesnoj.ads_loader_bot_web.state.chat;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_service.service.CategoryService;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.SpotService;
import vnavesnoj.ads_loader_bot_service.service.UserService;
import vnavesnoj.ads_loader_bot_web.factory.builderassistant.FilterBuilderAssistantFactory;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class BuilderStartChatState extends BaseChatState {

    private final ChatStateEnum chatStateName = ChatStateEnum.BUILDER_START;

    public BuilderStartChatState(UserService userService,
                                 FilterBuilderService filterBuilderService,
                                 MessageSource messageSource,
                                 CategoryService categoryService,
                                 SpotService spotService,
                                 FilterBuilderAssistantFactory filterBuilderAssistantFactory) {
        super(userService, filterBuilderService, categoryService, spotService, filterBuilderAssistantFactory, messageSource);
    }

    @Override
    public ChatStateEnum getName() {
        return this.chatStateName;
    }
}
