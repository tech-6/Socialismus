package me.whereareiam.socialismus.api.model.chat;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.config.chat.Chat;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class InternalChat extends Chat {
	private boolean vanillaSending;
	private char symbol;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InternalChat chat) {
			return chat.getSymbol() == this.getSymbol()
					&& chat.isVanillaSending() == this.isVanillaSending()
					&& chat.getId().equals(this.getId());
		}

		return false;
	}
}
