package vnavesnoj.ads_loader_bot_web.assistant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern.Fields;
import vnavesnoj.ads_loader_bot_service.dto.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.SpotService;
import vnavesnoj.ads_loader_bot_web.exception.UnknownInputFieldException;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxFilterBuilderAssistant implements FilterBuilderAssistant {

    private final FilterBuilderService filterBuilderService;
    private final SpotService spotService;
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    private final AnalyzerEnum analyzer = AnalyzerEnum.OLX_UA_DEFAULT;

    @Override
    public AnalyzerEnum getAnalyzer() {
        return analyzer;
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> getCurrentFilterBuilder(FilterBuilderReadDto filterBuilder, Long chatId, Locale locale) {
        return this.getFilterBuilderMessage(chatId, locale, filterBuilder, null);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> createNewFilterBuilder(Long userId, Long chatId, Integer spotId, Locale locale) {
        final var createdBuilder = Optional.of(OlxDefaultPattern.builder()
                        .priceType(PriceType.ALL)
                        .minPrice(0L)
                        .currencyCode(CurrencyCode.UAH)
                        .build())
                .map(pattern -> new FilterBuilderCreateDto(pattern, spotId, userId))
                .map(filterBuilderService::create)
                .orElseThrow(RuntimeException::new);
        final var header = messageSource.getMessage("bot.create.filter-builder-created", null, locale) + "\n\n";
        return getFilterBuilderMessage(chatId, locale, createdBuilder, header);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Override
    public BaseRequest<SendMessage, SendResponse> createInputRequest(FilterBuilderReadDto filterBuilder, String inputField, Long chatId, Locale locale) {
        final OlxDefaultPattern pattern = objectMapper.readValue(filterBuilder.getPattern(), OlxDefaultPattern.class);
        return switch (inputField) {
            case OlxDefaultPattern.Fields.descriptionPatterns ->
                    onChooseInputDescriptionPatterns(pattern, chatId, locale);
            case OlxDefaultPattern.Fields.priceType -> onChooseInputPriceType(chatId, locale);
            case OlxDefaultPattern.Fields.minPrice -> onChooseInputMinPrice(chatId, locale);
            case OlxDefaultPattern.Fields.maxPrice -> onChooseInputMaxPrice(chatId, locale);
            case OlxDefaultPattern.Fields.currencyCode -> onChooseInputCurrencyCode(chatId, locale);
            case OlxDefaultPattern.Fields.cityNames -> onChooseInputCityNames(chatId, locale);
            case OlxDefaultPattern.Fields.regionNames -> onChooseInputRegionNames(chatId, locale);
            default ->
                    throw new UnknownInputFieldException("unknown input field '" + inputField + "' for create input request");
        };
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputDescriptionPatterns(OlxDefaultPattern pattern, Long chatId, Locale locale) {
        final String descriptionPatterns = pattern.getDescriptionPatterns() == null || pattern.getDescriptionPatterns().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(pattern.getDescriptionPatterns());
        final var message = messageSource.getMessage(
                "bot.create.input.description-patterns.formatted",
                new Object[]{descriptionPatterns},
                locale
        );
        final var helpButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.help", null, locale)
        ).callbackData("/help " + Fields.descriptionPatterns);
        final var backButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.back", null, locale)
        ).callbackData("/builder");
        final var keyboard = new InlineKeyboardMarkup(helpButton, backButton);
        return new SendMessage(chatId, message)
                .parseMode(ParseMode.Markdown)
                .replyMarkup(keyboard);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputPriceType(Long chatId, Locale locale) {
        return null;
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputMinPrice(Long chatId, Locale locale) {
        return null;
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputMaxPrice(Long chatId, Locale locale) {
        return null;
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputCurrencyCode(Long chatId, Locale locale) {
        return null;
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputCityNames(Long chatId, Locale locale) {
        return null;
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputRegionNames(Long chatId, Locale locale) {
        return null;
    }

    @NonNull
    @SneakyThrows(JsonProcessingException.class)
    private SendMessage getFilterBuilderMessage(Long chatId, Locale locale, FilterBuilderReadDto filterBuilder, String header) {
        final OlxDefaultPattern pattern = objectMapper.readValue(filterBuilder.getPattern(), OlxDefaultPattern.class);

        final String descriptionPatterns = pattern.getDescriptionPatterns() == null || pattern.getDescriptionPatterns().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(pattern.getDescriptionPatterns());

        final String priceType = pattern.getPriceType() == null
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : messageSource.getMessage(pattern.getPriceType().getMessageSource(), null, locale);

        final var minPrice = pattern.getMinPrice() == null ? 0L : pattern.getMinPrice();
        final var maxPrice = pattern.getMaxPrice() == null
                ? messageSource.getMessage("bot.filter.info.max", null, locale)
                : pattern.getMaxPrice();

        final String city = pattern.getCityNames() == null || pattern.getCityNames().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(pattern.getCityNames());

        final String region = pattern.getRegionNames() == null || pattern.getRegionNames().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(pattern.getRegionNames());

        final String message = (header == null ? "" : header) +
                messageSource.getMessage("bot.filter.info.platform.formatted", new Object[]{filterBuilder.getSpot().getPlatform().getDomain()}, locale) + '\n' +
                messageSource.getMessage("bot.filter.info.category.formatted", new Object[]{filterBuilder.getSpot().getName()}, locale) + '\n' +
                messageSource.getMessage("bot.create.fill-in-the-details", null, locale) + ':';
        final var patternButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.description-patterns", new Object[]{descriptionPatterns}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + Fields.descriptionPatterns);
        final var priceTypeButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.price-type", new Object[]{priceType}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + Fields.priceType);
        final var minPriceButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.min-price", new Object[]{minPrice}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + Fields.minPrice);
        final var maxPriceButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.max-price", new Object[]{maxPrice}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + Fields.maxPrice);
        final var currencyCodeButton = new InlineKeyboardButton(
                pattern.getCurrencyCode().name()
        ).callbackData("/input " + filterBuilder.getId() + " " + Fields.currencyCode);
        final var cityButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.city", new Object[]{city}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + Fields.cityNames);
        final var regionButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.region", new Object[]{region}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + Fields.regionNames);
        final var keybord = new InlineKeyboardMarkup()
                .addRow(patternButton)
                .addRow(priceTypeButton)
                .addRow(minPriceButton, maxPriceButton, currencyCodeButton)
                .addRow(cityButton, regionButton);
        return new SendMessage(chatId, message)
                .parseMode(ParseMode.Markdown)
                .replyMarkup(keybord);
    }
}
