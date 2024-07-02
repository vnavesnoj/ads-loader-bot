package vnavesnoj.ads_loader_bot_web.assistant;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderAssistant extends Assistant {

    BaseRequest<SendMessage, SendResponse> getCurrentFilterBuilderRequest(Long filterBuilderId);

    BaseRequest<SendMessage, SendResponse> createNewFilterBuilder(Long userId, Long chatId, Integer spotId, Locale locale);
}
