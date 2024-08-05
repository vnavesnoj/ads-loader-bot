package vnavesnoj.ads_loader_bot_web.assistant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_web.assistant.component.FilterBuilderInfoProducer;
import vnavesnoj.ads_loader_bot_web.assistant.component.FilterBuilderManager;
import vnavesnoj.ads_loader_bot_web.assistant.component.InputRequestCreator;

import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxFilterBuilderAssistant implements FilterBuilderAssistant {

    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    private final InputRequestCreator<OlxDefaultPattern> olxDefaultInputRequestCreator;
    private final FilterBuilderInfoProducer olxDefaultFilterBuilderInfoProducer;
    private final FilterBuilderManager olxDefaultFilterBuilderManager;

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
        final var createdBuilder = olxDefaultFilterBuilderManager.create(spotId, userId);
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
        return olxDefaultFilterBuilderManager.resetField(filterBuilder.getId(), inputField);
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
        return olxDefaultFilterBuilderManager.updateField(filterBuilder.getId(), currentInput, input);
    }

    @Override
    public String handleInputRequest(FilterBuilderReadDto filterBuilder, @NonNull String field, String value) {
        return olxDefaultFilterBuilderManager.updateField(filterBuilder.getId(), field, value);
    }
}