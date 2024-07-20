package me.whereareiam.socialismus.platform.paper.renderer;

import io.papermc.paper.chat.ChatRenderer;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SocialismusRenderer implements ChatRenderer {
    private final FormattedChatMessage formattedChatMessage;

    public SocialismusRenderer(FormattedChatMessage formattedChatMessage) {
        this.formattedChatMessage = formattedChatMessage;
    }

    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .matchLiteral("{message}")
                .replacement(formattedChatMessage.getContent())
                .build();

        return formattedChatMessage.getFormat().replaceText(replacement);
    }
}
