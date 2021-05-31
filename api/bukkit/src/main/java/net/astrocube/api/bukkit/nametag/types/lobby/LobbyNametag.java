package net.astrocube.api.bukkit.nametag.types.lobby;

import net.astrocube.api.bukkit.nametag.types.AbstractNametag;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class LobbyNametag extends AbstractNametag {

	public LobbyNametag(Player recipient, String tag, String teamName) {
		super(recipient, tag, teamName);
	}

	public static Builder builder(Player recipient) {
		return new Builder(recipient);
	}

	public static class Builder extends AbstractNametag.Builder<LobbyNametag, Builder> {

		private String prefix = "";

		private Builder(Player recipient) {
			super(recipient);
		}

		@Override
		public LobbyNametag build() {
			String playerName = this.recipient.getName();
			String tag = this.prefix + playerName;

			return new LobbyNametag(
				this.recipient,
				tag,
				this.priority + StringUtils.substring(playerName, 0, 15)
			);
		}

		public Builder prefix(String prefix) {
			this.prefix = prefix;
			return this;
		}
	}
}