package me.whereareiam.socialismus.api.model.chat.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.ComponentUtil;
import me.whereareiam.socialismus.api.model.chat.Chat;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import net.kyori.adventure.text.Component;

import java.io.*;
import java.util.Base64;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
public class ChatMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int id;
    private final DummyPlayer sender;
    private Set<DummyPlayer> recipients;

    private transient Component content;
    private Chat chat;
    private boolean cancelled;
    private boolean vanillaSending;

    public static String serialize(ChatMessage chatMessage) throws IOException {
        ChatMessage tempChatMessage = chatMessage.toBuilder().build();
        tempChatMessage.setRecipients(Collections.emptySet());

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(tempChatMessage);

            return Base64.getEncoder().encodeToString(bos.toByteArray());
        }
    }

    public static ChatMessage deserialize(String content) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(content));
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return (ChatMessage) in.readObject();
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(ComponentUtil.toString(content));
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        content = ComponentUtil.toGson(in.readUTF());
    }
}
