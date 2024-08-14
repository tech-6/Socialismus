package me.whereareiam.socialismus.api.model.player;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class DummyCommandPlayer extends DummyPlayer {
    private Object commandSender;
}
