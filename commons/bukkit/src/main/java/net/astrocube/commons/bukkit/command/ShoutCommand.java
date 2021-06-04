package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Switch;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.channel.MatchMessageBroadcaster;
import net.astrocube.api.bukkit.game.channel.MatchShoutoutCooldown;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class ShoutCommand implements CommandClass {

	private @Inject ActualMatchCache actualMatchCache;
	private @Inject MessageHandler messageHandler;
	private @Inject MatchMessageBroadcaster matchMessageBroadcaster;
	private @Inject MatchShoutoutCooldown matchShoutoutCooldown;
	private @Inject Plugin plugin;

	@Command(names = {"shout"})
	public boolean onShoutCommand(@Sender Player player, @Switch("g") boolean global, @Text String message) {

		try {
			Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

			if (!matchOptional.isPresent()) {
				messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.not-active");
				return true;
			}

			boolean spectating = UserMatchJoiner.checkOrigin(player.getDatabaseIdentifier(), matchOptional.get()) == UserMatchJoiner.Origin.SPECTATING;

			if ((global || spectating) && !player.hasPermission("commands.staff.shout")) {
				messageHandler.sendIn(player, AlertModes.ERROR, "commands.command.no-permission");
				return true;
			}

			if (!global && !player.hasPermission("commands.staff.shout") &&
				matchShoutoutCooldown.hasCooldown(player.getDatabaseIdentifier())) {
				messageHandler.sendIn(player, AlertModes.ERROR, "game.shout-cooldown");
				return true;
			}

			if ((!spectating && !global) && matchOptional.get().getTeams().stream().noneMatch(t -> t.getMembers().size() > 1)) {
				messageHandler.sendIn(player, AlertModes.ERROR, "game.shout-solo");
				return true;
			}

			matchMessageBroadcaster.sendMessage(message, player, true, (global || spectating));

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error while performing shoutout", e);
		}

		return true;
	}
}