package vnavesnoj.ads_loader_bot_web.state.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_common.constant.Platform;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface ChatState {

    BaseRequest<SendMessage, SendResponse> onCreate(User user, Chat chat);

    BaseRequest<SendMessage, SendResponse> onForceCreate(User user, Chat chat);

    BaseRequest<SendMessage, SendResponse> onChoosePlatform(User user, Chat chat, Platform platform);

    BaseRequest<SendMessage, SendResponse> onChooseCategory(User user, Chat chat, Integer categoryId);

    ChatStateEnum getName();
}
