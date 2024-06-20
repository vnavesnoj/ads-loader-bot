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
import org.springframework.data.domain.PageRequest;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_service.service.CategoryService;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.SpotService;
import vnavesnoj.ads_loader_bot_service.service.UserService;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
public abstract class BaseChatState implements ChatState {

    private final UserService userService;
    private final FilterBuilderService filterBuilderService;
    private final CategoryService categoryService;
    private final SpotService spotService;
    private final MessageSource messageSource;

    @Override
    public BaseRequest<SendMessage, SendResponse> onCreate(User user, Chat chat) {
        final var locale = Locale.of(user.languageCode());
        if (filterBuilderService.findByUserId(user.id()).isPresent()) {
            return writeFilterBuilderExists(chat, locale);
        }
        return onForceCreate(user, chat);
    }

    private SendMessage writeFilterBuilderExists(Chat chat, Locale locale) {
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

    @Override
    public BaseRequest<SendMessage, SendResponse> onForceCreate(User user, Chat chat) {
        filterBuilderService.deleteByUserId(user.id());
        final var locale = Locale.of(user.languageCode());
        final var buttons = Arrays.stream(Platform.values())
                .map(item -> new InlineKeyboardButton(item.getDomain()).callbackData("/platform " + item.name()))
                .toArray(InlineKeyboardButton[]::new);
        final var keybord = new InlineKeyboardMarkup(buttons);
        final var message = messageSource.getMessage("bot.create.choose-platform", null, locale);
        final var response = new SendMessage(chat.id(), message)
                .replyMarkup(keybord);
        userService.updateChatState(user.id(), ChatStateEnum.BUILDER_START);
        return response;
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onChoosePlatform(User user, Chat chat, Platform platform) {
        final var locale = Locale.of(user.languageCode());
        if (filterBuilderService.findByUserId(user.id()).isPresent()) {
            return writeFilterBuilderExists(chat, locale);
        }
        final var atomicInteger = new AtomicInteger(0);
        final var keyboard = new InlineKeyboardMarkup();
        categoryService.findAllByPlatform(platform, PageRequest.of(0, 50)).stream()
                .map(item -> new InlineKeyboardButton(item.getName()).callbackData("/category " + item.getId())
                )
                .collect(Collectors.groupingBy(item -> atomicInteger.getAndIncrement() / 2))
                .values()
                .stream()
                .map(list -> list.toArray(InlineKeyboardButton[]::new))
                .forEach(keyboard::addRow);
        final var message = messageSource.getMessage("bot.create.search-platform", new Object[]{platform.getDomain()}, locale) +
                '\n' +
                messageSource.getMessage("bot.create.choose-category", null, locale);
        return new SendMessage(chat.id(), message)
                .replyMarkup(keyboard);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onChooseCategory(User user, Chat chat, Integer categoryId) {
        final var locale = Locale.of(user.languageCode());
        if (filterBuilderService.findByUserId(user.id()).isPresent()) {
            return writeFilterBuilderExists(chat, locale);
        }
        final var maybeCategory = categoryService.findById(categoryId);
        if (maybeCategory.isEmpty()) {
            return writeCategoryNotExists(categoryId, chat, locale);
        }
        final var categoryDto = maybeCategory.get();
        final var atomicInteger = new AtomicInteger(0);
        final var keyboard = new InlineKeyboardMarkup();
        spotService.findAllByCategoryId(categoryId, PageRequest.of(0, 50)).stream()
                .map(item -> new InlineKeyboardButton(item.getName()).callbackData("/spot " + item.getId())
                )
                .collect(Collectors.groupingBy(item -> atomicInteger.getAndIncrement() / 2))
                .values()
                .stream()
                .map(list -> list.toArray(InlineKeyboardButton[]::new))
                .forEach(keyboard::addRow);
        final var message = messageSource.getMessage("bot.create.search-platform", new Object[]{categoryDto.getPlatform().getDomain()}, locale) +
                '\n' +
                messageSource.getMessage("bot.create.category", new Object[]{categoryDto.getName()}, locale) +
                '\n' +
                messageSource.getMessage("bot.create.choose-spot", null, locale);
        return new SendMessage(chat.id(), message)
                .replyMarkup(keyboard);
    }

    private BaseRequest<SendMessage, SendResponse> writeCategoryNotExists(Integer categoryId, Chat chat, Locale locale) {
        final var message = messageSource.getMessage("bot.category-not-exists", null, locale);
        return new SendMessage(chat.id(), message);
    }
}
