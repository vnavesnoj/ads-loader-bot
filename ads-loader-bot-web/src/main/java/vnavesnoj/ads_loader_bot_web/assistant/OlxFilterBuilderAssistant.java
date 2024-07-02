package vnavesnoj.ads_loader_bot_web.assistant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.dto.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.service.SpotService;

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
    public BaseRequest<SendMessage, SendResponse> getCurrentFilterBuilderRequest(Long filterBuilderId) {
        return null;
    }

    @SneakyThrows
    @Override
    public BaseRequest<SendMessage, SendResponse> createNewFilterBuilder(Long userId, Long chatId, Integer spotId, Locale locale) {
        final var createdBuilder = Optional.of(OlxDefaultPattern.builder()
                        .priceType(PriceType.ALL)
                        .build())
                .map(pattern -> new FilterBuilderCreateDto(pattern, spotId, userId))
                .map(filterBuilderService::create)
                .orElseThrow(RuntimeException::new);
        final OlxDefaultPattern pattern = objectMapper.readValue(createdBuilder.getPattern(), OlxDefaultPattern.class);

        final String descriptionPatterns = pattern.getDescriptionPatterns() == null || pattern.getDescriptionPatterns().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale) + ';'
                : Arrays.toString(pattern.getDescriptionPatterns()) + ';';

        final String priceType = pattern.getPriceType() == null
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale) + ';'
                : messageSource.getMessage(pattern.getPriceType().getMessageSource(), null, locale) + ';';

        String price;
        if (pattern.getMinPrice() == null && pattern.getMaxPrice() == null) {
            price = messageSource.getMessage("bot.filter.info.not-indicated", null, locale) + ';';
        } else if (pattern.getMinPrice() != null && pattern.getMaxPrice() != null) {
            price = pattern.getMinPrice() + " - " + pattern.getMaxPrice() + ' ' + pattern.getCurrencyCode() + ';';
        } else if (pattern.getMinPrice() != null) {
            price = messageSource.getMessage("bot.filter.info.price-from", new Object[]{pattern.getMinPrice(), pattern.getCurrencyCode()}, locale) + ';';
        } else {
            price = messageSource.getMessage("bot.filter.info.price-to", new Object[]{pattern.getMaxPrice(), pattern.getCurrencyCode()}, locale) + ';';
        }

        final String city = pattern.getCityNames() == null || pattern.getCityNames().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale) + ';'
                : Arrays.toString(pattern.getCityNames()) + ';';

        final String region = pattern.getRegionNames() == null || pattern.getRegionNames().length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale) + ';'
                : Arrays.toString(pattern.getRegionNames()) + ';';

        final String message = messageSource.getMessage("bot.create.filter-builder-created", null, locale) + "\n\n" +
                messageSource.getMessage("bot.filter.info.platform", new Object[]{createdBuilder.getSpot().getPlatform().getDomain()}, locale) + '\n' +
                messageSource.getMessage("bot.category", new Object[]{createdBuilder.getSpot().getName()}, locale) + '\n' +
                messageSource.getMessage("bot.create.fill-in-the-details", null, locale) + ":\n" +
                "1. " + messageSource.getMessage("bot.filter.info.description-patterns", new Object[]{descriptionPatterns}, locale) + '\n' +
                "2. " + messageSource.getMessage("bot.filter.info.price-type", new Object[]{priceType}, locale) + '\n' +
                "3. " + messageSource.getMessage("bot.filter.info.price", new Object[]{price}, locale) + '\n' +
                "4. " + messageSource.getMessage("bot.filter.info.city", new Object[]{city}, locale) + '\n' +
                "5. " + messageSource.getMessage("bot.filter.info.region", new Object[]{region}, locale) + '\n';
        return new SendMessage(chatId, message);
    }
}
