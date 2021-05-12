package net.astrocube.commons.bukkit.punishment.freeze;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.freeze.FreezeRequestAlerter;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

@Singleton
public class CoreFreezeRequestAlerter implements FreezeRequestAlerter {

	private @Inject MessageHandler messageHandler;

	@Override
	public void alert(Player player) {

		Title title = new Title(
			messageHandler.get(player, "punish.freeze.alert.title"),
			messageHandler.get(player, "punish.freeze.alert.sub")
		);

		player.sendTitle(title);
		player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1f, 1f);
		messageHandler.send(player, "punish.freeze.alert.text");

	}

	@Override
	public void alertUnfreeze(Player player) {
		messageHandler.sendIn(player, AlertModes.INFO, "punish.freeze.unfreeze");
	}

}
