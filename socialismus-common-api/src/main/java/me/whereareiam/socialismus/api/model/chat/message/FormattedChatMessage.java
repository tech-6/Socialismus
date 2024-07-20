package me.whereareiam.socialismus.api.model.chat.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import net.kyori.adventure.text.Component;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class FormattedChatMessage extends ChatMessage {
    private Component format;
}