package me.whereareiam.socialismus.adapter.config.template.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.adapter.config.dynamic.ChatsConfig;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.api.model.config.chat.ChatFormat;
import me.whereareiam.socialismus.api.model.config.chat.ChatParameters;
import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.api.type.chat.ChatType;

import java.util.List;

@Singleton
public class ChatTemplate implements DefaultConfig<ChatsConfig> {
	@Override
	public ChatsConfig getDefault() {
		ChatsConfig chatsConfig = new ChatsConfig();

		// Default values
		Chat local = new Chat(
				"local",
				0,
				true,
				new ChatParameters(
						ChatType.LOCAL,
						200
				),
				List.of(
						new ChatFormat(
								"<gray>[L] {playerName}: <white>{message}",
								""
						),
						new ChatFormat(
								"<gray>[L] {playerName}: <gold>{message}",
								"socialismus.admin"
						)
				)
		);

		Chat global = new Chat(
				"global",
				1,
				true,
				new ChatParameters(
						ChatType.GLOBAL,
						0
				),
				List.of(
						new ChatFormat(
								"<gray>[G] {playerName}: <white>{message}",
								""
						),
						new ChatFormat(
								"<gray>[G] {playerName}: <gold>{message}",
								"socialismus.admin"
						)
				)
		);

		chatsConfig.getChats().addAll(List.of(local, global));

		return chatsConfig;
	}
}