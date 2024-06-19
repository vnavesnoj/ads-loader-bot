package vnavesnoj.ads_loader_bot_web.state.chat;

import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface ChatStateFactory {

    ChatState getChatStateByName(ChatStateEnum chatStateName);
}
