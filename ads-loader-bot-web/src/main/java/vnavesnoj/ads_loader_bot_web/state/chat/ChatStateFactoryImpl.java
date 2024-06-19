package vnavesnoj.ads_loader_bot_web.state.chat;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;
import vnavesnoj.ads_loader_bot_web.exception.ChatStateInitializationException;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class ChatStateFactoryImpl implements ChatStateFactory {

    private final Map<ChatStateEnum, ChatState> chatStates;

    //TODO need tests in future
    public ChatStateFactoryImpl(List<ChatState> chatStates) {
        final var duplicateImplementations = getDuplicateImplementations(chatStates);
        final var notImplementedChatStates = Arrays.stream(ChatStateEnum.values())
                .filter(item -> chatStates.stream()
                        .noneMatch(state -> state.getName() == item))
                .toList();
        if (duplicateImplementations.isEmpty() && notImplementedChatStates.isEmpty()) {
            this.chatStates = new HashMap<>();
            chatStates.forEach(item -> this.chatStates.put(item.getName(), item));
        } else if (duplicateImplementations.isEmpty()) {
            throw new ChatStateInitializationException("there are not implemented chat states: " + notImplementedChatStates);
        } else {
            final var duplications = duplicateImplementations.stream()
                    .map(item -> item.getClass().getName() + "(" + item.getName() + ")")
                    .collect(Collectors.joining(",", "[", "]"));
            throw new ChatStateInitializationException("there are duplicated implementations: " + duplications);
        }
    }

    @Override
    public ChatState getChatStateByName(ChatStateEnum chatStateName) {
        return chatStates.get(chatStateName);
    }

    private List<ChatState> getDuplicateImplementations(List<ChatState> chatStates) {
        Set<ChatStateEnum> chatStateEnums = new HashSet<>();
        return chatStates.stream()
                .filter(not(item -> chatStateEnums.add(item.getName())))
                .distinct()
                .toList();
    }
}
