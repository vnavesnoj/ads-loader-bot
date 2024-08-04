package vnavesnoj.ads_loader_bot_web.assistant.component;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.Nullable;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;

import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderInfoProducer {

    BaseRequest<SendMessage, SendResponse> getFilterBuilderInfo(FilterBuilderReadDto filterBuilder,
                                                                Long chatId,
                                                                Locale locale,
                                                                @Nullable String header);
}
