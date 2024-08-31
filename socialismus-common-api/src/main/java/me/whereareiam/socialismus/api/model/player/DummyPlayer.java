package me.whereareiam.socialismus.api.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.chat.Chat;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class DummyPlayer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String username;
    private final UUID uniqueId;
    private final transient Audience audience;

    @Setter
    private String location; // can be worldName, serverName or null
    @Setter
    private Locale locale;
    @Setter
    private Chat lastChat;

    public void sendMessage(Component component) {
        audience.sendMessage(component);
    }
}
