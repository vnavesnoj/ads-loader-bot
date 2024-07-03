package vnavesnoj.ads_loader_bot_web.assistant;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;

import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderAssistant extends Assistant {

    BaseRequest<SendMessage, SendResponse> getCurrentFilterBuilder(FilterBuilderReadDto filterBuilder, Long chatId, Locale locale);

    BaseRequest<SendMessage, SendResponse> createNewFilterBuilder(Long userId, Long chatId, Integer spotId, Locale locale);

    BaseRequest<SendMessage, SendResponse> createInputRequest(FilterBuilderReadDto filterBuilder, String inputField, Long chatId, Locale locale);
}
