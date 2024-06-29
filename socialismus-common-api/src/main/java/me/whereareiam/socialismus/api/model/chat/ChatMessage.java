package me.whereareiam.socialismus.api.model.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import net.kyori.adventure.text.Component;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ChatMessage {
	private final DummyPlayer sender;
	private final Set<UUID> recipients;

	private Component content;
	private Chat chat;
	private boolean cancelled;
}
