package vnavesnoj.ads_loader_bot_web.controller.bot;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_service.dto.user.UserCreateDto;
import vnavesnoj.ads_loader_bot_service.service.UserService;
import vnavesnoj.ads_loader_bot_web.handler.BotRequestHandler;
import vnavesnoj.ads_loader_bot_web.state.chat.ChatStateFactory;

import java.util.Locale;


/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Log4j2
@BotController
public class TelegramFilterManagerBot implements TelegramMvcController {

    private final String token;
    private final UserService userService;
    private final ChatStateFactory chatStateFactory;
    private final BotRequestHandler botRequestHandler;

    private final MessageSource messageSource;

    public TelegramFilterManagerBot(@Value("${telegram.bot.filter-manager.token}")
                                    String token,
                                    UserService userService,
                                    ChatStateFactory chatStateFactory,
                                    BotRequestHandler tgFilterBotRequestHandler,
                                    MessageSource messageSource) {
        this.token = token;
        this.userService = userService;
        this.chatStateFactory = chatStateFactory;
        this.botRequestHandler = tgFilterBotRequestHandler;
        this.messageSource = messageSource;
    }

    @PostConstruct
    void init() {
        log.info("Bot init. Token: " + token);
    }

    @Override
    public String getToken() {
        return token;
    }

    //TODO chatState.onStart()
    @BotRequest(value = "/start", type = {MessageType.MESSAGE, MessageType.CALLBACK_QUERY})
    public BaseRequest<SendMessage, SendResponse> start(User user, Chat chat) {
        userService.create(new UserCreateDto(user.id(), user.languageCode(), ChatStateEnum.START));
        final var message = messageSource.getMessage(
                "bot.greeting",
                new Object[]{user.username()},
                Locale.of(user.languageCode())
        );
        return new SendMessage(chat.id(), message);
    }

    @MessageRequest("/null")
    public BaseRequest<SendMessage, SendResponse> onNull(User user, Chat chat) {
        return null;
    }

    @MessageRequest("/hello")
    public BaseRequest<SendMessage, SendResponse> hello(User user, Chat chat) {
        return new SendMessage(chat.id(), "Hello, " + user.firstName() + "!");
    }

    @MessageRequest("/hello {name:[\\s\\S]+}")
    public String helloWithName(@BotPathVariable("name") String userName) {
        // Return a string if you need to reply with a simple message
        return "Hello, " + userName;
    }

    @BotRequest(value = "/create", type = {MessageType.MESSAGE, MessageType.CALLBACK_QUERY})
    public BaseRequest<SendMessage, SendResponse> create(User user, Chat chat) {
        return botRequestHandler.handleRequest(
                user,
                chat,
                chatState -> chatState.onCreate(user, chat)
        );
    }

    @BotRequest(value = "/platform {platform:[\\S]+}", type = MessageType.CALLBACK_QUERY)
    public BaseRequest<SendMessage, SendResponse> onChoosePlatform(User user,
                                                                   Chat chat,
                                                                   @BotPathVariable("platform") String platform) {
        return botRequestHandler.handleRequest(
                user,
                chat,
                chatState -> chatState.onChoosePlatform(user, chat, Platform.valueOf(platform))
        );
    }

    @BotRequest(value = "/category {categoryId:[\\d]+}", type = MessageType.CALLBACK_QUERY)
    public BaseRequest<SendMessage, SendResponse> onChooseCategory(User user,
                                                                   Chat chat,
                                                                   @BotPathVariable("categoryId") Integer categoryId) {
        return botRequestHandler.handleRequest(
                user,
                chat,
                chatState -> chatState.onChooseCategory(user, chat, categoryId)
        );
    }

    @BotRequest(value = "/spot {spotId:[\\d]+}", type = MessageType.CALLBACK_QUERY)
    public BaseRequest<SendMessage, SendResponse> onChooseSpot(User user,
                                                               Chat chat,
                                                               @BotPathVariable("spotId") Integer spotId) {
        return botRequestHandler.handleRequest(
                user,
                chat,
                chatState -> chatState.onChooseSpot(user, chat, spotId)
        );
    }

    @BotRequest(value = "/force-create", type = {MessageType.MESSAGE, MessageType.CALLBACK_QUERY})
    public BaseRequest<SendMessage, SendResponse> onForceCreate(User user, Chat chat) {
        return botRequestHandler.handleRequest(
                user,
                chat,
                chatState -> chatState.onForceCreate(user, chat)
        );
    }

    @BotRequest(value = "/builder", type = {MessageType.MESSAGE, MessageType.CALLBACK_QUERY})
    public BaseRequest<SendMessage, SendResponse> onBuilder(User user, Chat chat) {
        return botRequestHandler.handleRequest(
                user,
                chat,
                chatState -> chatState.onBuilder(user, chat)
        );
    }

    @BotRequest(value = "/input {fbId:[\\d]+} {input:[\\S]+}", type = MessageType.CALLBACK_QUERY)
    public BaseRequest<SendMessage, SendResponse> onChooseInput(User user,
                                                                Chat chat,
                                                                @BotPathVariable("fbId") Long filterBuilderId,
                                                                @BotPathVariable("input") String input) {
        return botRequestHandler.handleRequest(
                user,
                chat,
                chatState -> chatState.onChooseInput(user, chat, filterBuilderId, input)
        );
    }

    @BotRequest(value = "{input:[\\s\\S]+}", type = {MessageType.MESSAGE, MessageType.CALLBACK_QUERY})
    public BaseRequest<SendMessage, SendResponse> onInput(User user,
                                                          Chat chat,
                                                          @BotPathVariable("input") String input) {
        return botRequestHandler.handleRequest(
                user,
                chat,
                chatState -> chatState.onInput(user, chat, input)
        );
    }
}
