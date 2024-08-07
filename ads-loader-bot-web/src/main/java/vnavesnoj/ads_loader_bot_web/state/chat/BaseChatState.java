package vnavesnoj.ads_loader_bot_web.state.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_service.service.CategoryService;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.SpotService;
import vnavesnoj.ads_loader_bot_service.service.UserService;
import vnavesnoj.ads_loader_bot_web.exception.FilterBuilderNotFoundException;
import vnavesnoj.ads_loader_bot_web.exception.InvalidChatStateMethod;
import vnavesnoj.ads_loader_bot_web.factory.builderassistant.FilterBuilderAssistantFactory;

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
    private final FilterBuilderAssistantFactory filterBuilderAssistantFactory;
    private final MessageSource messageSource;

    @NotNull
    protected static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onCreate(User user, Chat chat) {
        final var locale = getLocale();
        if (filterBuilderService.findByUserId(user.id()).isPresent()) {
            return writeFilterBuilderExists(chat, locale);
        }
        return onForceCreate(user, chat);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onBuilder(User user, Chat chat) {
        final var locale = getLocale();
        return filterBuilderService.findByUserId(user.id())
                .map(item -> {
                    final var message = filterBuilderAssistantFactory.getAssistant(item.getSpot().getAnalyzer())
                            .getCurrentFilterBuilder(item, chat.id(), locale);
                    userService.updateChatState(user.id(), ChatStateEnum.BUILDER_INFO);
                    return message;
                })
                .orElseGet(() -> this.onBuilderWhenEmpty(chat.id(), locale));
    }

    private BaseRequest<SendMessage, SendResponse> onBuilderWhenEmpty(Long chatId, Locale locale) {
        final var button = new InlineKeyboardButton(
                messageSource.getMessage("bot.create.button.create-new-filter", null, locale)
        ).callbackData("/force-create");
        final var keybord = new InlineKeyboardMarkup()
                .addRow(button);
        return new SendMessage(chatId, messageSource.getMessage("bot.create.builder-is-empty", null, locale))
                .replyMarkup(keybord);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onForceCreate(User user, Chat chat) {
        filterBuilderService.deleteByUserId(user.id());
        final var locale = getLocale();
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
        final var locale = getLocale();
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
        final var locale = getLocale();
        if (filterBuilderService.findByUserId(user.id()).isPresent()) {
            return writeFilterBuilderExists(chat, locale);
        }
        final var maybeCategory = categoryService.findById(categoryId);
        if (maybeCategory.isEmpty()) {
            return writeCategoryNotExists(chat, locale);
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
                messageSource.getMessage("bot.filter.info.category", new Object[]{categoryDto.getName()}, locale) +
                '\n' +
                messageSource.getMessage("bot.create.choose-spot", null, locale);
        return new SendMessage(chat.id(), message)
                .replyMarkup(keyboard);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onChooseSpot(User user, Chat chat, Integer spotId) {
        final var locale = getLocale();
        if (filterBuilderService.findByUserId(user.id()).isPresent()) {
            return writeFilterBuilderExists(chat, locale);
        }
        final var maybeSpot = spotService.findById(spotId);
        if (maybeSpot.isEmpty()) {
            return writeSpotNotExists(chat, locale);
        }
        final var message = filterBuilderAssistantFactory.getAssistant(maybeSpot.get().getAnalyzer())
                .createNewFilterBuilder(user.id(), chat.id(), spotId, locale);
        userService.updateChatState(user.id(), ChatStateEnum.BUILDER_INFO);
        return message;
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onChooseInput(User user, Chat chat, Long filterBuilderId, String input) {
        final var locale = getLocale();
        return filterBuilderService.findByIdAndUserId(filterBuilderId, user.id())
                .map(item -> {
                    final var response = filterBuilderAssistantFactory.getAssistant(item.getSpot().getAnalyzer())
                            .createInputRequest(item, input, chat.id(), locale);
                    userService.updateChatState(user.id(), ChatStateEnum.BUILDER_INPUT);
                    filterBuilderService.updateCurrentInput(item.getId(), input);
                    return response;
                })
                .orElseThrow(() -> new FilterBuilderNotFoundException("FilterBuilder with id = " + filterBuilderId
                        + " and FilterBuilder.user.id = " + user.id()
                        + " does not exist"));
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onChooseResetInput(User user,
                                                                     Chat chat,
                                                                     Long filterBuilderId,
                                                                     String input) {
        filterBuilderService.findByIdAndUserId(filterBuilderId, user.id())
                .map(item -> filterBuilderAssistantFactory.getAssistant(item.getSpot().getAnalyzer())
                        .resetInput(item, input))
                .orElseThrow(() -> new FilterBuilderNotFoundException("FilterBuilder with id = " + filterBuilderId
                        + " and FilterBuilder.user.id = " + user.id()
                        + " does not exist"));
        return onBuilder(user, chat);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onInput(User user, Chat chat, String input) {
        throw new InvalidChatStateMethod("method 'onInput' not supported by " + this.getClass().getName());
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> onInputValue(User user, Chat chat, Long filterBuilderId, String patternField, String value) {
        filterBuilderService.findByIdAndUserId(filterBuilderId, user.id())
                .map(item -> filterBuilderAssistantFactory.getAssistant(item.getSpot().getAnalyzer())
                        .handleInputRequest(item, patternField, value))
                .orElseThrow(() -> new FilterBuilderNotFoundException("FilterBuilder with id = " + filterBuilderId
                        + " and FilterBuilder.user.id = " + user.id()
                        + " does not exist"));
        return onBuilder(user, chat);
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

    private BaseRequest<SendMessage, SendResponse> writeCategoryNotExists(Chat chat, Locale locale) {
        final var message = messageSource.getMessage("bot.category-not-exists", null, locale);
        return new SendMessage(chat.id(), message);
    }

    private BaseRequest<SendMessage, SendResponse> writeSpotNotExists(Chat chat, Locale locale) {
        final var message = messageSource.getMessage("bot.spot-not-exists", null, locale);
        return new SendMessage(chat.id(), message);
    }
}
