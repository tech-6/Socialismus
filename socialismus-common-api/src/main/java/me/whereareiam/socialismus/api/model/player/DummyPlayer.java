package me.whereareiam.socialismus.api.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Locale;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class DummyPlayer {
	private final String username;
	private final UUID uniqueId;

	private final String location; // can be worldName or serverName
	private final Locale locale;
}
