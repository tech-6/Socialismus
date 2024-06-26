package me.whereareiam.socialismus.adapter.config.template.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.chat.ChatMessages;
import me.whereareiam.socialismus.api.output.DefaultConfig;

@Singleton
public class ChatMessagesTemplate implements DefaultConfig<ChatMessages> {
	@Override
	public ChatMessages getDefault() {
		ChatMessages chatMessages = new ChatMessages();

		// Default values
		chatMessages.setNoChatMatch(" <gold>ꜱᴏᴄɪᴀʟɪꜱᴍᴜꜱ <dark_gray>| <white>No chat matching criteria found.");

		return chatMessages;
	}
}
