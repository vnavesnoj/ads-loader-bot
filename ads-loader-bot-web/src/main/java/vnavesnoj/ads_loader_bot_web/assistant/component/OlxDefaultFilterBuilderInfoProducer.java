package vnavesnoj.ads_loader_bot_web.assistant.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;

import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultFilterBuilderInfoProducer implements FilterBuilderInfoProducer {

    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @SneakyThrows(JsonProcessingException.class)
    @Override
    public BaseRequest<SendMessage, SendResponse> getFilterBuilderInfo(FilterBuilderReadDto filterBuilder, Long chatId, Locale locale, @Nullable String header) {
        final OlxDefaultPattern pattern = objectMapper.readValue(filterBuilder.getPattern(), OlxDefaultPattern.class);

        final String descriptionPatterns = pattern.getDescriptionPatterns(messageSource, locale);
        final String priceType = pattern.getPriceType(messageSource, locale);
        final String minPrice = pattern.getMinPriceString();
        final String maxPrice = pattern.getMaxPriceString(messageSource, locale);
        final String currencyCode = pattern.getCurrencyCode(messageSource, locale);
        final String city = pattern.getCityNames(messageSource, locale);
        final String region = pattern.getRegionNames(messageSource, locale);

        final String message = (header == null ? "" : header) +
                messageSource.getMessage("bot.filter.info.platform.formatted", new Object[]{filterBuilder.getSpot().getPlatform().getDomain()}, locale) + '\n' +
                messageSource.getMessage("bot.filter.info.category.formatted", new Object[]{filterBuilder.getSpot().getName()}, locale) + '\n' +
                messageSource.getMessage("bot.create.fill-in-the-details", null, locale) + ':';
        final var patternButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.description-patterns", new Object[]{descriptionPatterns}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + OlxDefaultPattern.Fields.descriptionPatterns);
        final var priceTypeButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.price-type", new Object[]{priceType}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + OlxDefaultPattern.Fields.priceType);
        final var minPriceButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.min-price", new Object[]{minPrice}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + OlxDefaultPattern.Fields.minPrice);
        final var maxPriceButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.max-price", new Object[]{maxPrice}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + OlxDefaultPattern.Fields.maxPrice);
        final var currencyCodeButton = new InlineKeyboardButton(
                currencyCode
        ).callbackData("/input " + filterBuilder.getId() + " " + OlxDefaultPattern.Fields.currencyCode);
        final var cityButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.city", new Object[]{city}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + OlxDefaultPattern.Fields.cityNames);
        final var regionButton = new InlineKeyboardButton(
                messageSource.getMessage("bot.filter.info.region", new Object[]{region}, locale)
        ).callbackData("/input " + filterBuilder.getId() + " " + OlxDefaultPattern.Fields.regionNames);
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
