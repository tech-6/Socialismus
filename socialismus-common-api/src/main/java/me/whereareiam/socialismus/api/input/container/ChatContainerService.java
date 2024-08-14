package me.whereareiam.socialismus.api.input.container;

import me.whereareiam.socialismus.api.model.chat.Chat;
import me.whereareiam.socialismus.api.model.chat.InternalChat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatContainerService {
    void addChat(InternalChat chat);

    void addChat(Chat chat);

    boolean hasChat(String id);

    boolean hasChatBySymbol(String symbol);

    Optional<InternalChat> getChat(String id);

    List<InternalChat> getChatBySymbol(String symbol);

    Set<InternalChat> getChats();
}
