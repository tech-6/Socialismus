package me.whereareiam.socialismus.api.model.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.function.Function;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ChatWorker {
	private final Function<ChatMessage, ChatMessage> function;
	private final int priority;
	private boolean removable;
	private boolean cancelled;
}
