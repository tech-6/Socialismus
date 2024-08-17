package me.whereareiam.socialismus.api.input.chat;

public interface ChatHistoryService {
    boolean removeMessage(int id);

    int removeMessages(int amount);

    int removeMessages(String username);
}
