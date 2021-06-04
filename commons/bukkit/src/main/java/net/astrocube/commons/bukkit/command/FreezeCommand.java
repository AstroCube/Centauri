package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.freeze.FreezeRequestAlerter;
import net.astrocube.api.bukkit.punishment.freeze.FrozenUserProvider;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandClass {

	private @Inject MessageHandler messageHandler;
	private @Inject FrozenUserProvider frozenUserProvider;
	private @Inject FreezeRequestAlerter freezeRequestAlerter;

	@Command(names = "freeze", permission = "commons.staff.freeze")
	public boolean freezeCommand(@Sender Player player, String targetName) {

		Player target = Bukkit.getPlayer(targetName);

		if (target == null) {
			messageHandler.sendIn(player, AlertModes.ERROR, "commands.unknown-player");
			return true;
		}

		if (checkFreezingAvailability(player, target)) {
			return true;
		}

		if (frozenUserProvider.isFrozen(target.getPlayer())) {
			frozenUserProvider.unFreeze(target.getPlayer());
			freezeRequestAlerter.alertUnfreeze(target.getPlayer());
			messageHandler.sendReplacingIn(
				player, AlertModes.MUTED, "punish.freeze.feedback.unfreeze",
				"%target%", target.getName()
			);
		} else {
			frozenUserProvider.freeze(target.getPlayer());
			freezeRequestAlerter.alert(target.getPlayer());
			messageHandler.sendReplacingIn(
				player, AlertModes.MUTED, "punish.freeze.feedback.freeze",
				"%target%", target.getName()
			);
		}

		return true;
	}

	private boolean checkFreezingAvailability(@Sender Player player, Player target) {

		if (target.getPlayer().hasPermission("commons.staff.freeze.exempt")) {
			messageHandler.sendIn(player, AlertModes.ERROR, "punish.freeze.exempt");
			return true;
		}

		if (player.getName().equalsIgnoreCase(target.getName())) {
			messageHandler.sendIn(player, AlertModes.ERROR, "punish.freeze.same");
			return true;
		}

		return false;
	}

}