package me.whereareiam.socialismus.api.model.chat;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class InternalChat extends Chat {
    private boolean vanillaSending;

    public static InternalChat from(Chat chat) {
        return InternalChat.builder()
                .id(chat.getId())
                .priority(chat.getPriority())
                .enabled(chat.isEnabled())
                .parameters(chat.getParameters())
                .formats(chat.getFormats())
                .requirements(chat.getRequirements())
                .vanillaSending(true)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InternalChat chat) {
            return Objects.equals(chat.getParameters().getSymbol(), this.getParameters().getSymbol())
                    && chat.isVanillaSending() == this.isVanillaSending()
                    && chat.getId().equals(this.getId());
        }

        return false;
    }
}
