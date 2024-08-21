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

    public static DummyCommandPlayer from(DummyPlayer player, Object commandSender) {
        return DummyCommandPlayer.builder()
                .commandSender(commandSender)
                .username(player.getUsername())
                .uniqueId(player.getUniqueId())
                .audience(player.getAudience())
                .location(player.getLocation())
                .locale(player.getLocale())
                .build();
    }
}
