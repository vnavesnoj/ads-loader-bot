package vnavesnoj.ads_loader_bot_web.assistant.component;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultInputRequestCreator implements InputRequestCreator<OlxDefaultPattern> {

    private final MessageSource messageSource;

    @Override
    public BaseRequest<SendMessage, SendResponse> createInputRequest(Long id, OlxDefaultPattern pattern, String inputField, Long chatId, Locale locale) {
        return switch (inputField) {
            case OlxDefaultPattern.Fields.descriptionPatterns ->
                    onChooseInputDescriptionPatterns(id, pattern, chatId, locale);
            case OlxDefaultPattern.Fields.priceType -> onChooseInputPriceType(id, pattern, chatId, locale);
            case OlxDefaultPattern.Fields.minPrice -> onChooseInputMinPrice(id, pattern, chatId, locale);
            case OlxDefaultPattern.Fields.maxPrice -> onChooseInputMaxPrice(id, pattern, chatId, locale);
            case OlxDefaultPattern.Fields.currencyCode -> onChooseInputCurrencyCode(id, pattern, chatId, locale);
            case OlxDefaultPattern.Fields.cityNames -> onChooseInputCityNames(id, pattern, chatId, locale);
            case OlxDefaultPattern.Fields.regionNames -> onChooseInputRegionNames(id, pattern, chatId, locale);
            default ->
                    throw new UnknownInputFieldException("unknown input field '" + inputField + "' for create input request");
        };
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputDescriptionPatterns(Long id,
                                                                                    OlxDefaultPattern pattern,
                                                                                    Long chatId,
                                                                                    Locale locale) {
        final var message = messageSource.getMessage(
                "bot.create.input.description-patterns.formatted",
                new Object[]{pattern.getDescriptionPatterns(messageSource, locale)},
                locale
        );
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(getDefaultButtons(id, OlxDefaultPattern.Fields.descriptionPatterns, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputPriceType(Long id,
                                                                          OlxDefaultPattern pattern,
                                                                          Long chatId,
                                                                          Locale locale) {
        final var message = messageSource.getMessage(
                "bot.create.input.price-type.formatted",
                new Object[]{pattern.getPriceType(messageSource, locale)},
                locale
        );
        final var freeTypeButton = new InlineKeyboardButton(
                messageSource.getMessage(PriceType.FREE.getMessageSource(), null, locale)
        ).callbackData("/input-value %s %s %s".formatted(id, OlxDefaultPattern.Fields.priceType, PriceType.FREE.name()));
        final var exchangeTypeButton = new InlineKeyboardButton(
                messageSource.getMessage(PriceType.EXCHANGE.getMessageSource(), null, locale)
        ).callbackData("/input-value %s %s %s".formatted(id, OlxDefaultPattern.Fields.priceType, PriceType.EXCHANGE.name()));
        final var paidTypeButton = new InlineKeyboardButton(
                messageSource.getMessage(PriceType.PAID.getMessageSource(), null, locale)
        ).callbackData("/input-value %s %s %s".formatted(id, OlxDefaultPattern.Fields.priceType, PriceType.PAID.name()));
        final var allTypeButton = new InlineKeyboardButton(
                messageSource.getMessage(PriceType.ALL.getMessageSource(), null, locale)
        ).callbackData("/input-value %s %s %s".formatted(id, OlxDefaultPattern.Fields.priceType, PriceType.ALL.name()));
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(freeTypeButton, exchangeTypeButton, paidTypeButton)
                .addRow(allTypeButton)
                .addRow(getDefaultButtons(id, OlxDefaultPattern.Fields.priceType, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputMinPrice(Long id,
                                                                         OlxDefaultPattern pattern,
                                                                         Long chatId,
                                                                         Locale locale) {
        final var currencyCode = pattern.getCurrencyCode('(' + messageSource.getMessage(
                "bot.filter.info.currency-code-not-indicated",
                null,
                locale
        ) + ')');
        final var message = messageSource.getMessage(
                "bot.create.input.min-price.formatted",
                new Object[]{pattern.getMinPriceString(), currencyCode},
                locale
        );
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(getDefaultButtons(id, OlxDefaultPattern.Fields.minPrice, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputMaxPrice(Long id,
                                                                         OlxDefaultPattern pattern,
                                                                         Long chatId,
                                                                         Locale locale) {
        final var currencyCode = pattern.getCurrencyCode('(' + messageSource.getMessage(
                "bot.filter.info.currency-code-not-indicated",
                null,
                locale
        ) + ')');
        final var message = messageSource.getMessage(
                "bot.create.input.max-price.formatted",
                new Object[]{pattern.getMaxPriceString(messageSource, locale), currencyCode},
                locale
        );
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(getDefaultButtons(id, OlxDefaultPattern.Fields.maxPrice, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputCurrencyCode(Long id,
                                                                             OlxDefaultPattern pattern,
                                                                             Long chatId,
                                                                             Locale locale) {
        final var currencyCode = pattern.getCurrencyCode(messageSource.getMessage(
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
                .map(item -> new InlineKeyboardButton(item.name())
                        .callbackData(
                                "/input-value %s %s %s".formatted(id, OlxDefaultPattern.Fields.currencyCode, item.name())
                        )
                )
                .toArray(InlineKeyboardButton[]::new);
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(codeButtons)
                .addRow(getDefaultButtons(id, OlxDefaultPattern.Fields.currencyCode, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputCityNames(Long id,
                                                                          OlxDefaultPattern pattern,
                                                                          Long chatId,
                                                                          Locale locale) {
        final var message = messageSource.getMessage(
                "bot.create.input.city-patterns.formatted",
                new Object[]{pattern.getCityNames(messageSource, locale)},
                locale
        );
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(getDefaultButtons(id, OlxDefaultPattern.Fields.cityNames, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    private BaseRequest<SendMessage, SendResponse> onChooseInputRegionNames(Long id,
                                                                            OlxDefaultPattern pattern,
                                                                            Long chatId,
                                                                            Locale locale) {
        final var message = messageSource.getMessage(
                "bot.create.input.region-patterns.formatted",
                new Object[]{pattern.getRegionNames(messageSource, locale)},
                locale);
        final var keyboard = new InlineKeyboardMarkup()
                .addRow(getDefaultButtons(id, OlxDefaultPattern.Fields.regionNames, locale));
        return new SendMessage(chatId, message)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.Markdown);
    }

    @NonNull
    private InlineKeyboardButton[] getDefaultButtons(Long id, String patternField, Locale locale) {
        final var resetButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.reset", null, locale)
        ).callbackData("/reset-input %s %s".formatted(id, patternField));
        final var helpButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.help", null, locale)
        ).callbackData("/help " + patternField);
        final var backButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.button.back", null, locale)
        ).callbackData("/builder");
        return new InlineKeyboardButton[]{resetButton, helpButton, backButton};
    }
}
