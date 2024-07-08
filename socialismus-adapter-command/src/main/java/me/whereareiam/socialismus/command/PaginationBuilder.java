package me.whereareiam.socialismus.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.config.settings.Settings;

@Singleton
public class PaginationBuilder {
	private final Settings settings;
	private final Messages messages;

	@Inject
	public PaginationBuilder(Settings settings, Messages messages) {
		this.settings = settings;
		this.messages = messages;
	}

	public String build(String message, int totalCommands, int currentPage) {
		int commandsPerPage = settings.getMisc().getCommandsPerPage();
		int totalPages = (int) Math.ceil((double) totalCommands / commandsPerPage);

		if (totalPages <= 1 && !messages.getCommands().getPagination().isShowPaginationIfOnePage()) {
			return message.replace("{pagination}", "");
		}

		String pagination = formatPagination(currentPage, totalPages);
		return message.replace("{pagination}", pagination);
	}

	private String formatPagination(int currentPage, int totalPages) {
		String previousLink = buildPreviousLink(currentPage);
		String nextLink = buildNextLink(currentPage, totalPages);

		String paginationFormat = messages.getCommands().getPagination().getFormat();
		return paginationFormat.replace("{previous}", previousLink).replace("{next}", nextLink).replace("{current}", Integer.toString(currentPage)).replace("{max}", Integer.toString(totalPages));
	}

	private String buildPreviousLink(int currentPage) {
		if (currentPage > 1) {
			return messages.getCommands().getPagination().getPreviousTagFormat().replace("{previousPage}", Integer.toString(currentPage - 1));
		}
		return messages.getCommands().getPagination().isShowPreviousEvenIfFirst() ? messages.getCommands().getPagination().getPreviousTagFormat().replace("{previousPage}", "1") : "";
	}

	private String buildNextLink(int currentPage, int totalPages) {
		if (currentPage < totalPages) {
			return messages.getCommands().getPagination().getNextTagFormat().replace("{nextPage}", Integer.toString(currentPage + 1));
		}
		return messages.getCommands().getPagination().isShowNextEvenIfLast() ? messages.getCommands().getPagination().getNextTagFormat().replace("{nextPage}", Integer.toString(totalPages)) : "";
	}
}