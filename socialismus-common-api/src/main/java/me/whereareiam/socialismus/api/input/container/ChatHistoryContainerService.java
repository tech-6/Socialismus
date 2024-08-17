package me.whereareiam.socialismus.api.input.container;

import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatHistoryContainerService {
    void addMessage(int id, FormattedChatMessage message);

    boolean removeMessage(int id);

    int removeMessages(int amount);

    Optional<FormattedChatMessage> getMessage(int id);

    List<FormattedChatMessage> getMessages(String username);

    List<FormattedChatMessage> getMessages(UUID uniqueId);

    List<FormattedChatMessage> getMessages();
}
