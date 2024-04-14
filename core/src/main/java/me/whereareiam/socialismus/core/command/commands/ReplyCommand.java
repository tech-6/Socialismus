package me.whereareiam.socialismus.core.command.commands;

import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Syntax;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.core.command.base.CommandBase;
import me.whereareiam.socialismus.core.command.commands.privatemessage.PrivateMessagingService;
import me.whereareiam.socialismus.core.config.command.CommandsConfig;
import me.whereareiam.socialismus.core.config.message.MessagesConfig;
import me.whereareiam.socialismus.core.util.FormatterUtil;
import me.whereareiam.socialismus.core.util.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@Singleton
public class ReplyCommand extends CommandBase {
	private final Injector injector;
	private final FormatterUtil formatterUtil;
	private final MessageUtil messageUtil;
	private final CommandsConfig commands;
	private final MessagesConfig messages;

	private final PrivateMessagingService messagingService;

	@Inject
	public ReplyCommand(Injector injector, FormatterUtil formatterUtil, MessageUtil messageUtil, CommandsConfig commands,
	                    MessagesConfig messages, PrivateMessagingService messagingService) {
		this.injector = injector;
		this.formatterUtil = formatterUtil;
		this.messageUtil = messageUtil;
		this.commands = commands;
		this.messages = messages;
		this.messagingService = messagingService;
	}

	@CommandAlias("%command.reply")
	@CommandPermission("%permission.reply")
	@Description("%description.replyCommand")
	public void onCommand(CommandIssuer issuer) {
		if (!issuer.isPlayer()) {
			messageUtil.sendMessage(issuer, messages.commands.onlyForPlayer);
			return;
		}

		messageUtil.sendMessage(issuer, messages.commands.wrongSyntax);
	}

	@CommandAlias("%command.reply")
	@CommandPermission("%permission.reply")
	@Description("%description.replyCommand")
	@Syntax("%syntax.reply")
	public void onCommand(CommandIssuer issuer, String message) {
		if (!issuer.isPlayer()) {
			messageUtil.sendMessage(issuer, messages.commands.onlyForPlayer);
			return;
		}

		Player sender = issuer.getIssuer();
		Player recipient = messagingService.getLastSender(sender);

		if (recipient == null) {
			messageUtil.sendMessage(sender, messages.commands.replyCommand.noRecipient);
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
		return commands.replyCommand.enabled == commands.privateMessageCommand.enabled;
	}

	@Override
	public void addReplacements() {
		commandHelper.addReplacement(commands.replyCommand.command, "command.reply");
		commandHelper.addReplacement(commands.replyCommand.permission, "permission.reply");
		commandHelper.addReplacement(messages.commands.replyCommand.description, "description.replyCommand");
		commandHelper.addReplacement(commands.replyCommand.syntax, "syntax.reply");
	}
}
