package me.whereareiam.socialismus.common.container;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.ChatHistoryContainerService;
import me.whereareiam.socialismus.api.model.chat.ChatSettings;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;

import java.util.*;

@Singleton
public class ChatHistoryContainer implements ChatHistoryContainerService {
    private final Map<Integer, FormattedChatMessage> chatHistory;

    @Inject
    public ChatHistoryContainer(Provider<ChatSettings> chatSettings) {
        this.chatHistory = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, FormattedChatMessage> eldest) {
                return size() > chatSettings.get().getHistory().getHistorySize();
            }
        };
    }

    @Override
    public void addMessage(int id, FormattedChatMessage message) {
        chatHistory.put(id, message);
    }

    @Override
    public boolean removeMessage(int id) {
        return chatHistory.remove(id) != null;
    }

    @Override
    public int removeMessages(int amount) {
        int removed = 0;
        for (int i = 0; i < amount; i++)
            if (chatHistory.remove(i) != null)
                removed++;

        return removed;
    }

    @Override
    public Optional<FormattedChatMessage> getMessage(int id) {
        return chatHistory.containsKey(id) ? Optional.of(chatHistory.get(id)) : Optional.empty();
    }

    @Override
    public List<FormattedChatMessage> getMessages(String username) {
        return chatHistory.values().parallelStream()
                .filter(message -> message.getSender().getUsername().equals(username))
                .toList();
    }

    @Override
    public List<FormattedChatMessage> getMessages(UUID uniqueId) {
        return chatHistory.values().parallelStream()
                .filter(message -> message.getSender().getUniqueId().equals(uniqueId))
                .toList();
    }

    @Override
    public List<FormattedChatMessage> getMessages() {
        return new ArrayList<>(chatHistory.values());
    }
}
