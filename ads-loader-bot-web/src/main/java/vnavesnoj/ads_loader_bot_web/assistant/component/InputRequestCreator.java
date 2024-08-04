package vnavesnoj.ads_loader_bot_web.assistant.component;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface InputRequestCreator<T> {

    BaseRequest<SendMessage, SendResponse> createInputRequest(T pattern, String inputField, Long chatId, Locale locale);
}
