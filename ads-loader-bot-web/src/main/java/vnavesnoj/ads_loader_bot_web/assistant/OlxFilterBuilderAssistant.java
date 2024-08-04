package vnavesnoj.ads_loader_bot_web.assistant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_web.assistant.component.FilterBuilderInfoProducer;
import vnavesnoj.ads_loader_bot_web.assistant.component.InputRequestCreator;
import vnavesnoj.ads_loader_bot_web.assistant.component.PatternValueMapper;
import vnavesnoj.ads_loader_bot_web.exception.FilterBuilderNotFoundException;

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
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    private final PatternValueMapper olxDefaultPatternValueMapper;
    private final InputRequestCreator<OlxDefaultPattern> olxDefaultInputRequestCreator;
    private final FilterBuilderInfoProducer olxDefaultFilterBuilderInfoProducer;

    private final OlxDefaultPattern DEFAULT_PATTERN = OlxDefaultPattern.builder()
            .priceType(PriceType.ALL)
            .minPrice(0L)
            .currencyCode(CurrencyCode.UAH)
            .build();

    private final Class<? extends OlxDefaultPattern> DEFAULT_PATTERN_CLASS = DEFAULT_PATTERN.getClass();

    private final AnalyzerEnum analyzer = AnalyzerEnum.OLX_UA_DEFAULT;

    @Override
    public AnalyzerEnum getAnalyzer() {
        return analyzer;
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> getCurrentFilterBuilder(FilterBuilderReadDto filterBuilder,
                                                                          Long chatId,
                                                                          Locale locale) {
        return olxDefaultFilterBuilderInfoProducer.getFilterBuilderInfo(filterBuilder, chatId, locale, null);
    }

    @Override
    public BaseRequest<SendMessage, SendResponse> createNewFilterBuilder(Long userId,
                                                                         Long chatId,
                                                                         Integer spotId,
                                                                         Locale locale) {
        final var createdBuilder = Optional.of(DEFAULT_PATTERN)
                .map(pattern -> new FilterBuilderCreateDto(pattern, spotId, userId))
                .map(filterBuilderService::create)
                .orElseThrow();
        final var header = messageSource.getMessage("bot.create.filter-builder-created", null, locale) + "\n\n";
        return olxDefaultFilterBuilderInfoProducer.getFilterBuilderInfo(createdBuilder, chatId, locale, header);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Override
    public BaseRequest<SendMessage, SendResponse> createInputRequest(FilterBuilderReadDto filterBuilder,
                                                                     String inputField,
                                                                     Long chatId,
                                                                     Locale locale) {
        final OlxDefaultPattern pattern = objectMapper.readValue(filterBuilder.getPattern(), OlxDefaultPattern.class);
        return olxDefaultInputRequestCreator.createInputRequest(filterBuilder.getId(), pattern, inputField, chatId, locale);
    }

    @SneakyThrows
    @Override
    public String resetInput(FilterBuilderReadDto filterBuilder, String inputField) {
        try {
            final var field = DEFAULT_PATTERN_CLASS.getDeclaredField(inputField);
            try {
                field.setAccessible(true);
                final var defaultValue = field.get(DEFAULT_PATTERN);
                filterBuilderService.updatePatternField(filterBuilder.getId(), inputField, defaultValue)
                        .orElseThrow(FilterBuilderNotFoundException::new);
                return defaultValue != null ? defaultValue.toString() : "";
            } finally {
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            throw new UnknownInputFieldException("field '%s' not exists in %s".formatted(inputField, DEFAULT_PATTERN_CLASS.getName()));
        }
    }

    @Override
    public String handleInputRequest(FilterBuilderReadDto filterBuilder, String input) {
        final var currentInput = filterBuilder.getCurrentInput();
        if (currentInput == null) {
            throw new NullPointerException("FilterBuilder with "
                    + FilterBuilderReadDto.Fields.id + " = "
                    + filterBuilder.getId() + " has null "
                    + FilterBuilderReadDto.Fields.currentInput);
        }
        final var mappedValue = olxDefaultPatternValueMapper.map(currentInput, input);
        filterBuilderService.updatePatternField(filterBuilder.getId(), currentInput, mappedValue);
        return input;
    }
}