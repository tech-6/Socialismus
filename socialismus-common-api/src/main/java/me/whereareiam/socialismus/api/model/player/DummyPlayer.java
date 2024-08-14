package me.whereareiam.socialismus.api.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.Locale;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class DummyPlayer {
    private final String username;
    private final UUID uniqueId;
    private final Audience audience;

    @Setter
    private String location; // can be worldName, serverName or null
    @Setter
    private Locale locale;

    public void sendMessage(Component component) {
        audience.sendMessage(component);
    }
}
