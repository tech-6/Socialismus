package me.whereareiam.socialismus.adapter.config.template.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.adapter.config.dynamic.ChatsConfig;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.api.model.config.chat.ChatFormat;
import me.whereareiam.socialismus.api.model.config.chat.ChatParameters;
import me.whereareiam.socialismus.api.model.requirement.PermissionRequirement;
import me.whereareiam.socialismus.api.model.requirement.WorldRequirement;
import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.api.type.chat.ChatType;
import me.whereareiam.socialismus.api.type.requirement.RequirementOperatorType;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				),
				Map.of(
						RequirementType.PERMISSION, PermissionRequirement.builder()
								.permissions(List.of("socialismus.chat.local"))
								.operator(RequirementOperatorType.BYPASS)
								.condition("has(permissions)")
								.expected("true")
								.build(),
						RequirementType.WORLD, WorldRequirement.builder()
								.worlds(List.of("world"))
								.operator(RequirementOperatorType.OPTIONAL)
								.condition("contains(worlds)")
								.expected("world")
								.build()
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
				),
				new HashMap<>()
		);

		chatsConfig.getChats().addAll(List.of(local, global));

		return chatsConfig;
	}
}