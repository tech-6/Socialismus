package me.whereareiam.socialismus.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Locale;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class DummyPlayer {
	private final String username;
	private final UUID uniqueId;
	private final Locale locale = Locale.ENGLISH;
}
