package me.whereareiam.socialismus.api.output.integration;

import me.whereareiam.socialismus.api.model.DummyPlayer;

public interface FormattingIntegration extends Integration {
	String format(DummyPlayer dummyPlayer, String content);
}
