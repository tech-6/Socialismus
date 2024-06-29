package me.whereareiam.socialismus.api.model.player;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class DummyCommandPlayer extends DummyPlayer {
	private final Object commandSender;
}
