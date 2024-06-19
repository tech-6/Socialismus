package me.whereareiam.socialismus.api.model.chat;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.config.chat.Chat;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class InternalChat extends Chat {
	private boolean vanillaSending = true;
	private char symbol;
}
