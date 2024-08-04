package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;

import java.util.List;

@Singleton
public class ChatBroadcaster {
    private final PlatformInteractor interactor;

    @Inject
    public ChatBroadcaster(PlatformInteractor interactor) {
        this.interactor = interactor;
    }

    public void broadcast(FormattedChatMessage chatMessage) {
        List<DummyPlayer> recipients = interactor.getPlayers(chatMessage.getRecipients());

        recipients.forEach(recipient -> recipient.getAudience().sendMessage(chatMessage.getFormat()));
    }
}
