package vnavesnoj.ads_loader_bot_web.handler;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import vnavesnoj.ads_loader_bot_web.state.chat.ChatState;

import java.util.function.Function;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface BotRequestHandler {

    BaseRequest<SendMessage, SendResponse> handleRequest(User user,
                                                         Chat chat,
                                                         Function<ChatState, BaseRequest<SendMessage, SendResponse>> function);
}
