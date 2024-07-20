package me.whereareiam.socialismus.adapter.config.dynamic;

import lombok.Getter;
import me.whereareiam.socialismus.api.model.chat.Chat;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatsConfig {
    private List<Chat> chats = new ArrayList<>();
}
