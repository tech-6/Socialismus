package me.whereareiam.socialismus.core.command.commands;

import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.tcoded.folialib.FoliaLib;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.api.type.BubbleTriggerType;
import me.whereareiam.socialismus.core.chat.message.ChatMessageFactory;
import me.whereareiam.socialismus.core.command.base.CommandBase;
import me.whereareiam.socialismus.core.config.command.CommandsConfig;
import me.whereareiam.socialismus.core.config.message.MessagesConfig;
import me.whereareiam.socialismus.core.module.bubblechat.BubbleChatService;
import me.whereareiam.socialismus.core.util.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;

@Singleton
@CommandAlias("%command.main")
public class BubbleCommand extends CommandBase {
	private final MessageUtil messageUtil;
	private final CommandsConfig commands;
	private final MessagesConfig messages;
	private final BubbleChatService bubbleChatService;
	private final ChatMessageFactory chatMessageFactory;
	private final FoliaLib foliaLib;

	@Inject
	public BubbleCommand(MessageUtil messageUtil, CommandsConfig commands,
	                     MessagesConfig messages, BubbleChatService bubbleChatService,
	                     ChatMessageFactory chatMessageFactory, FoliaLib foliaLib) {
		this.messageUtil = messageUtil;
		this.commands = commands;
		this.messages = messages;
		this.bubbleChatService = bubbleChatService;
		this.chatMessageFactory = chatMessageFactory;
		this.foliaLib = foliaLib;
	}

	@Subcommand("%command.bubble")
	@CommandPermission("%permission.bubble")
	@Description("%description.bubble")
	public void onCommand(CommandIssuer issuer) {
		if (!issuer.isPlayer()) {
			messageUtil.sendMessage(issuer, messages.commands.onlyForPlayer);
			return;
		}

		messageUtil.sendMessage(issuer, messages.commands.wrongSyntax);
	}

	@Subcommand("%command.bubble")
	@CommandCompletion("@nothing")
	@CommandPermission("%permission.bubble")
	@Description("%description.bubble")
	@Syntax("%syntax.bubble")
	public void onCommand(CommandIssuer issuer, String message) {
		if (!issuer.isPlayer())
			messageUtil.sendMessage(issuer, messages.commands.onlyForPlayer);

		Player player = issuer.getIssuer();

		foliaLib.getImpl().runAsync(a -> {
			ChatMessage chatMessage = chatMessageFactory.createChatMessage(player, List.of(), message, java.util.Optional.empty());
			bubbleChatService.distributeBubbleMessage(BubbleTriggerType.COMMAND, chatMessage);
		});

		messageUtil.sendMessage(player, messages.commands.bubbleCommand.success);
	}

	@Override
	public boolean isEnabled() {
		return commands.announceCommand.enabled;
	}

	@Override
	public void addReplacements() {
		commandHelper.addReplacement(commands.bubbleCommand.subCommand, "command.bubble");
		commandHelper.addReplacement(commands.bubbleCommand.permission, "permission.bubble");
		commandHelper.addReplacement(messages.commands.bubbleCommand.description, "description.bubble");
		commandHelper.addReplacement(commands.bubbleCommand.syntax, "syntax.bubble");
	}
}
