package me.whereareiam.socialismus.api.input.chat;

import me.whereareiam.socialismus.api.input.WorkerProcessor;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;

public interface ChatFormattingWorker extends WorkerProcessor<FormattedChatMessage> {
}
