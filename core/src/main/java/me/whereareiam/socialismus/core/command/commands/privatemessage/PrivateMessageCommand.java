package me.whereareiam.socialismus.core.command.commands.privatemessage;

import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.core.command.base.CommandBase;
import me.whereareiam.socialismus.core.config.command.CommandsConfig;
import me.whereareiam.socialismus.core.config.message.MessagesConfig;
import me.whereareiam.socialismus.core.util.FormatterUtil;
import me.whereareiam.socialismus.core.util.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Singleton
public class PrivateMessageCommand extends CommandBase {
	private final FormatterUtil formatterUtil;
	private final MessageUtil messageUtil;
	private final CommandsConfig commands;
	private final MessagesConfig messages;

	private final PrivateMessagingService messagingService;

	@Inject
	public PrivateMessageCommand(FormatterUtil formatterUtil, MessageUtil messageUtil, CommandsConfig commands, MessagesConfig messages,
	                             PrivateMessagingService messagingService) {
		this.formatterUtil = formatterUtil;
		this.messageUtil = messageUtil;
		this.commands = commands;
		this.messages = messages;
		this.messagingService = messagingService;
	}

	@CommandAlias("%command.privateMessage")
	@CommandPermission("%permission.privateMessage")
	@Description("%description.privateMessage")
	public void onCommand(CommandIssuer issuer) {
		if (!issuer.isPlayer()) {
			messageUtil.sendMessage(issuer, messages.commands.onlyForPlayer);
			return;
		}

		messageUtil.sendMessage(issuer, messages.commands.wrongSyntax);
	}

	@CommandAlias("%command.privateMessage")
	@CommandCompletion("@players @nothing")
	@CommandPermission("%permission.privateMessage")
	@Description("%description.privateMessage")
	@Syntax("%syntax.privateMessage")
	public void onCommand(CommandIssuer issuer, String targetPlayerName, String message) {
		if (!issuer.isPlayer())
			messageUtil.sendMessage(issuer, messages.commands.onlyForPlayer);

		Player sender = issuer.getIssuer();
		Player recipient = Bukkit.getPlayer(targetPlayerName);

		if (recipient == null) {
			messageUtil.sendMessage(sender, messages.commands.playerNotFound);
			return;
		}

		if (recipient == sender) {
			messageUtil.sendMessage(sender, messages.commands.samePlayer);
			return;
		}

		Component format = formatterUtil.formatMessage(sender, messages.commands.privateMessageCommand.format, true);

		format = messageUtil.replacePlaceholder(format, "{senderName}", sender.getName());
		format = messageUtil.replacePlaceholder(format, "{recipientName}", recipient.getName());
		format = messageUtil.replacePlaceholder(format, "{message}", message);

		messagingService.sendPrivateMessage(sender, recipient, format);
	}

	@Override
	public boolean isEnabled() {
		return commands.privateMessageCommand.enabled;
	}

	@Override
	public void addReplacements() {
		commandHelper.addReplacement(commands.privateMessageCommand.command, "command.privateMessage");
		commandHelper.addReplacement(commands.privateMessageCommand.permission, "permission.privateMessage");
		commandHelper.addReplacement(messages.commands.privateMessageCommand.description, "description.privateMessage");
		commandHelper.addReplacement(commands.privateMessageCommand.syntax, "syntax.privateMessage");
	}
}

