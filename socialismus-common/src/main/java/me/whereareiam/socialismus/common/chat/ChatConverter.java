package me.whereareiam.socialismus.common.chat;

import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.chat.Chat;

public class ChatConverter {
    public static InternalChat convert(Chat chat) {
        return InternalChat.builder()
                .id(chat.getId())
                .priority(chat.getPriority())
                .enabled(chat.isEnabled())
                .parameters(chat.getParameters())
                .formats(chat.getFormats())
                .requirements(chat.getRequirements())
                .vanillaSending(true)
                .build();
    }
}
