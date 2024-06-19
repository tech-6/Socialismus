package me.whereareiam.socialismus.api.model.config.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.type.chat.ChatTriggerType;
import me.whereareiam.socialismus.api.type.chat.ChatType;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Chat {
	private final String id;
	private final ChatType type;
	private final List<ChatTriggerType> triggers;
	private final List<ChatFormat> formats;
}
