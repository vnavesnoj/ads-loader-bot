package vnavesnoj.ads_loader_bot_web.state.chat;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_service.util.EnumImplementationAnalyzer;

import java.util.List;
import java.util.Map;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class ChatStateFactoryImpl implements ChatStateFactory {

    private final Map<ChatStateEnum, ChatState> chatStates;

    //TODO need tests in future
    public ChatStateFactoryImpl(List<ChatState> chatStates,
                                EnumImplementationAnalyzer enumAnalyzer) {
        this.chatStates = enumAnalyzer.getUniqueEnumImplementations(
                ChatStateEnum.class,
                chatStates,
                ChatState::getName
        );
    }

    @Override
    public ChatState getChatStateByName(ChatStateEnum chatStateName) {
        return chatStates.get(chatStateName);
    }
}
