package vnavesnoj.ads_loader_bot_web.controller.bot;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_service.factory.AnalyzerFactory;
import vnavesnoj.ads_loader_bot_service.service.CategoryService;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.UserService;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Log4j2
@BotController
public class TelegramFilterManagerBot implements TelegramMvcController {

    private final String token;
    private final UserService userService;
    private final CategoryService categoryService;
    private final FilterBuilderService filterBuilderService;
    private final AnalyzerFactory analyzerFactory;

    private final MessageSource messageSource;

    public TelegramFilterManagerBot(@Value("${telegram.bot.filter-manager.token}")
                                    String token,
                                    UserService userService,
                                    CategoryService categoryService,
                                    FilterBuilderService filterBuilderService,
                                    AnalyzerFactory analyzerFactory,
                                    MessageSource messageSource) {
        this.token = token;
        this.userService = userService;
        this.categoryService = categoryService;
        this.filterBuilderService = filterBuilderService;
        this.analyzerFactory = analyzerFactory;
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

    @MessageRequest("/hello")
    public BaseRequest<SendMessage, SendResponse> hello(User user, Chat chat) {
        return new SendMessage(chat.id(), "Hello, " + user.firstName() + "!");
    }

    @MessageRequest("/hello {name:[\\s\\S]+}")
    public String helloWithName(@BotPathVariable("name") String userName) {
        // Return a string if you need to reply with a simple message
        return "Hello, " + userName;
    }

    @MessageRequest("/create")
    public BaseRequest<SendMessage, SendResponse> create(User user, Chat chat) {
        final var locale = Locale.of(user.languageCode());
        final var buttons = Arrays.stream(Platform.values())
                .map(item -> new InlineKeyboardButton(item.getDomain()).callbackData(item.getDomain()))
                .toArray(InlineKeyboardButton[]::new);
        final var keybord = new InlineKeyboardMarkup(buttons);
        final var message = messageSource.getMessage("bot.create.choose-platform", null, locale);
        return new SendMessage(chat.id(), message)
                .replyMarkup(keybord);
    }

    @BotRequest(value = "olx.ua", type = MessageType.CALLBACK_QUERY)
    public BaseRequest<SendMessage, SendResponse> chooseCategoryForOlxUa(User user, Chat chat) {
        final var locale = Locale.of(user.languageCode());
        final var atomicInteger = new AtomicInteger(0);
        final var keyboard = new InlineKeyboardMarkup();
        categoryService.findAll(PageRequest.of(0, 50)).stream()
                .map(item -> new InlineKeyboardButton(item.getName()).callbackData("category=" + item.getId())
                )
                .collect(Collectors.groupingBy(item -> atomicInteger.getAndIncrement() / 2))
                .values()
                .stream()
                .map(list -> list.toArray(InlineKeyboardButton[]::new))
                .forEach(keyboard::addRow);
        final var message = messageSource.getMessage("bot.create.search-platform", new Object[]{Platform.OLXUA.getDomain()}, locale) +
                '\n' +
                messageSource.getMessage("bot.create.choose-category", null, locale);
        return new SendMessage(chat.id(), message)
                .replyMarkup(keyboard);
    }
}
