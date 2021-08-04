package net.astrocube.api.bukkit.party;

import net.astrocube.api.core.virtual.party.Party;
import org.bukkit.entity.Player;

/**
 * This class handle the chat in party
 */

public interface PartyMessenger {

	void sendMessage(Party party, String path, String... replacements);

	default void chat(Player sender, String message, Party party) {
		sendMessage(party, "chat-party",
			"%sender%", sender.getName(),
						"%message%", message);
	}

}
