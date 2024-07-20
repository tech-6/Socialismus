package me.whereareiam.socialismus.api.model.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.whereareiam.socialismus.api.type.chat.ChatType;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatParameters {
    private ChatType type;
    private String symbol;
    private int radius;
}
