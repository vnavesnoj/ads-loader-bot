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
            case OlxDefaultPattern.Fields.priceType -> onChooseInputPriceType(pattern, chatId, locale);
            case OlxDefaultPattern.Fields.minPrice -> onChooseInputMinPrice(pattern, chatId, locale);
            case OlxDefaultPattern.Fields.maxPrice -> onChooseInputMaxPrice(pattern, chatId, locale);
            case OlxDefaultPattern.Fields.currencyCode -> onChooseInputCurrencyCode(pattern, chatId, locale);
            case OlxDefaultPattern.Fields.cityNames -> onChooseInputCityNames(pattern, chatId, locale);
            case OlxDefaultPattern.Fields.regionNames -> onChooseInputRegionNames(pattern, chatId, locale);
            default ->
                    throw new UnknownInputFieldException("unknown input field '" + inputField + "' for create input request");
        };
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputDescriptionPatterns(OlxDefaultPattern pattern, Long chatId, Locale locale) {
        final String descriptionPatterns = getDescriptionPatterns(locale, pattern);
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

    private BaseRequest<SendMessage, SendResponse> onChooseInputPriceType(OlxDefaultPattern pattern, Long chatId, Locale locale) {
        final String priceType = getPriceType(locale, pattern);
        final var message = messageSource.getMessage(
                "bot.create.input.price-type.formatted",
                new Object[]{priceType},
                locale
        );
        final var freeTypeButton = new InlineKeyboardButton(
                messageSource.getMessage(PriceType.FREE.getMessageSource(), null, locale)
        ).callbackData(PriceType.FREE.name());
        final var exchangeTypeButton = new InlineKeyboardButton(
                messageSource.getMessage(PriceType.EXCHANGE.getMessageSource(), null, locale)
        ).callbackData(PriceType.EXCHANGE.name());
        final var paidTypeButton = new InlineKeyboardButton(
                messageSource.getMessage(PriceType.PAID.getMessageSource(), null, locale)
        ).callbackData(PriceType.PAID.name());
        final var allTypeButton = new InlineKeyboardButton(
                messageSource.getMessage(PriceType.ALL.getMessageSource(), null, locale)
        ).callbackData(PriceType.ALL.name());
        final var helpButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.help", null, locale)
        ).callbackData("/help " + Fields.priceType);
        final var backButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.back", null, locale)
        ).callbackData("/builder");
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(freeTypeButton, exchangeTypeButton, paidTypeButton)
                .addRow(allTypeButton)
                .addRow(helpButton, backButton);
        return new SendMessage(chatId, message)
                .parseMode(ParseMode.Markdown)
                .replyMarkup(keyboard);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputMinPrice(OlxDefaultPattern pattern,
                                                                         Long chatId,
                                                                         Locale locale) {
        final var minPrice = getMinPrice(pattern);
        final var currencyCode = getCurrencyCode(locale, pattern, '(' + messageSource.getMessage(
                "bot.filter.info.currency-code-not-indicated",
                null,
                locale
        ) + ')');
        final var message = messageSource.getMessage(
                "bot.create.input.min-price.formatted",
                new Object[]{minPrice, currencyCode},
                locale
        );
        final var helpButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.help", null, locale)
        ).callbackData("/help " + Fields.minPrice);
        final var backButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.back", null, locale)
        ).callbackData("/builder");
        final var keyboard = new InlineKeyboardMarkup().addRow(helpButton, backButton);
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputMaxPrice(OlxDefaultPattern pattern,
                                                                         Long chatId,
                                                                         Locale locale) {
        final var maxPrice = getMaxPrice(locale, pattern);
        final var currencyCode = getCurrencyCode(locale, pattern, '(' + messageSource.getMessage(
                "bot.filter.info.currency-code-not-indicated",
                null,
                locale
        ) + ')');
        final var message = messageSource.getMessage(
                "bot.create.input.max-price.formatted",
                new Object[]{maxPrice, currencyCode},
                locale
        );
        final var helpButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.help", null, locale)
        ).callbackData("/help " + Fields.maxPrice);
        final var backButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.back", null, locale)
        ).callbackData("/builder");
        final var keyboard = new InlineKeyboardMarkup().addRow(helpButton, backButton);
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputCurrencyCode(OlxDefaultPattern pattern,
                                                                             Long chatId,
                                                                             Locale locale) {
        final var currencyCode = getCurrencyCode(locale, pattern, messageSource.getMessage(
                "bot.filter.info.not-indicated",
                null,
                locale
        ));
        final var message = messageSource.getMessage(
                "bot.create.input.choose-currency-code.formatted",
                new Object[]{currencyCode},
                locale
        );
        final var codeButtons = Arrays.stream(CurrencyCode.values())
                .map(item -> new InlineKeyboardButton(item.name()).callbackData(item.name()))
                .toArray(InlineKeyboardButton[]::new);
        final var helpButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.help", null, locale)
        ).callbackData("/help " + Fields.currencyCode);
        final var backButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.back", null, locale)
        ).callbackData("/builder");
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(codeButtons)
                .addRow(helpButton, backButton);
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputCityNames(OlxDefaultPattern pattern,
                                                                          Long chatId,
                                                                          Locale locale) {
        final var message = messageSource.getMessage(
                "bot.create.input.city-patterns.formatted",
                new Object[]{getCity(locale, pattern)},
                locale
        );
        final var keyboard = new InlineKeyboardMarkup().addRow(getDefaultButtons(Fields.cityNames, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputRegionNames(OlxDefaultPattern pattern, Long chatId, Locale locale) {
        final var message = messageSource.getMessage(
                "bot.create.input.region-patterns.formatted",
                new Object[]{getRegion(locale, pattern)},
                locale);
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(getDefaultButtons(Fields.regionNames, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    @NonNull
    @SneakyThrows(JsonProcessingException.class)
    private SendMessage getFilterBuilderMessage(Long chatId, Locale locale, FilterBuilderReadDto filterBuilder, String header) {
        final OlxDefaultPattern pattern = objectMapper.readValue(filterBuilder.getPattern(), OlxDefaultPattern.class);

        final String descriptionPatterns = getDescriptionPatterns(locale, pattern);
        final String priceType = getPriceType(locale, pattern);
        final long minPrice = getMinPrice(pattern);
        final String maxPrice = getMaxPrice(locale, pattern);
        final String currencyCode = getCurrencyCode(
                locale,
                pattern,
                messageSource.getMessage("bot.filter.info.currency-code", null, locale));
        final String city = getCity(locale, pattern);
        final String region = getRegion(locale, pattern);

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
                currencyCode
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

    @NonNull
    private String getRegion(Locale locale, OlxDefaultPattern pattern) {
        return pattern.getRegionNames() == null || pattern.getRegionNames().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(pattern.getRegionNames());
    }

    @NonNull
    private String getCity(Locale locale, OlxDefaultPattern pattern) {
        return pattern.getCityNames() == null || pattern.getCityNames().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(pattern.getCityNames());
    }

    @NonNull
    private String getPriceType(Locale locale, OlxDefaultPattern pattern) {
        return pattern.getPriceType() == null
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : messageSource.getMessage(pattern.getPriceType().getMessageSource(), null, locale);
    }

    @NonNull
    private String getDescriptionPatterns(Locale locale, OlxDefaultPattern pattern) {
        return pattern.getDescriptionPatterns() == null || pattern.getDescriptionPatterns().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(pattern.getDescriptionPatterns());
    }

    @NonNull
    private String getMaxPrice(Locale locale, OlxDefaultPattern pattern) {
        return pattern.getMaxPrice() == null
                ? messageSource.getMessage("bot.filter.info.max", null, locale)
                : pattern.getMaxPrice().toString();
    }

    @NonNull
    private Long getMinPrice(OlxDefaultPattern pattern) {
        return pattern.getMinPrice() == null ? 0L : pattern.getMinPrice();
    }

    @NonNull
    private String getCurrencyCode(Locale locale, OlxDefaultPattern pattern, String defaultMessage) {
        return pattern.getCurrencyCode() == null
                ? defaultMessage
                : pattern.getCurrencyCode().name();
    }

    @NonNull
    private InlineKeyboardButton[] getDefaultButtons(String patternField, Locale locale) {
        final var helpButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.help", null, locale)
        ).callbackData("/help " + patternField);
        final var backButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.back", null, locale)
        ).callbackData("/builder");
        return new InlineKeyboardButton[]{helpButton, backButton};
    }
}
