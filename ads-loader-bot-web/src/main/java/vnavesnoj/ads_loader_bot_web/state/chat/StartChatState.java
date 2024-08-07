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
public class StartChatState extends BaseChatState {

    private final ChatStateEnum name = ChatStateEnum.START;

    public StartChatState(UserService userService,
                          FilterBuilderService filterBuilderService,
                          CategoryService categoryService,
                          SpotService spotService,
                          FilterBuilderAssistantFactory filterBuilderAssistantFactory,
                          MessageSource messageSource) {
        super(userService, filterBuilderService, categoryService, spotService, filterBuilderAssistantFactory, messageSource);
    }

    @Override
    public ChatStateEnum getName() {
        return name;
    }
}
