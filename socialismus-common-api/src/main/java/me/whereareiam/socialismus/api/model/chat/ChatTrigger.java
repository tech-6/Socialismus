package me.whereareiam.socialismus.api.model.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.whereareiam.socialismus.api.type.chat.ChatTriggerType;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ChatTrigger {
    private List<ChatTriggerType> triggers;
    private List<Character> symbols;
    private List<String> commands;
}
