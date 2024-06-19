package vnavesnoj.ads_loader_bot_web.state.chat;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_service.service.CategoryService;
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
    private final CategoryService categoryService;

    private final ChatStateEnum chatStateName = ChatStateEnum.BUILDER_START;

    public BuilderStartChatState(UserService userService,
                                 FilterBuilderService filterBuilderService,
                                 MessageSource messageSource,
                                 CategoryService categoryService) {
        super(userService, filterBuilderService, categoryService, messageSource);
        this.userService = userService;
        this.filterBuilderService = filterBuilderService;
        this.categoryService = categoryService;
        this.messageSource = messageSource;
    }

    @Override
    public ChatStateEnum getName() {
        return this.chatStateName;
    }
}
