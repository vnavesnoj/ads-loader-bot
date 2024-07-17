package vnavesnoj.ads_loader_bot_web.exception;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface BotResponseMessageException {

    BaseRequest<SendMessage, SendResponse> getResponseMessage(User user, Chat chat);
}
