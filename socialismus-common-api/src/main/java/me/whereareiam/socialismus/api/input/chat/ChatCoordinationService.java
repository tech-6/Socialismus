package me.whereareiam.socialismus.api.input.chat;

import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;

public interface ChatCoordinationService {
    FormattedChatMessage coordinate(ChatMessage chatMessage);
}
