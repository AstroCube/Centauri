package net.astrocube.commons.bukkit.listener.user;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.event.AuthenticationStartEvent;
import net.astrocube.api.bukkit.authentication.server.AuthenticationLimitValidator;
import net.astrocube.api.bukkit.punishment.PunishmentKickProcessor;
import net.astrocube.api.bukkit.session.SessionValidatorHandler;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class UserLoginListener implements Listener {

	private @Inject SessionValidatorHandler sessionValidatorHandler;
	private @Inject AuthenticationLimitValidator authenticationLimitValidator;
	private @Inject MessageHandler messageHandler;
	private @Inject PunishmentKickProcessor punishmentKickProcessor;
	private @Inject Plugin plugin;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onUserLogin(PlayerLoginEvent event) {

		SessionValidateDoc.Complete validator =
			sessionValidatorHandler.getValidationPendingUser(event.getPlayer().getUniqueId());

		if (validator == null) {
			event.setKickMessage(ChatColor.RED + "There was an error validating your session, please try again.");
			event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			return;
		}

		event.getPlayer().setDatabaseId(validator.getUser().getId());

		try {
			punishmentKickProcessor.validateKick(event.getPlayer(), validator.getUser());
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error while checking user punishments", e);
			event.getPlayer().kickPlayer(ChatColor.RED + "Error while checking your punishments registry.");
		}

		if (plugin.getConfig().getBoolean("authentication.enabled")) {

			long remaining = authenticationLimitValidator.getRemainingTime(event.getPlayer().getDatabaseId());
			if (remaining > 0) {
				event.setKickMessage(messageHandler.replacing(
						event.getPlayer(), "authentication.cooldown-await",
						"%time%", remaining / 1000 // TODO: Format the time
				));
				event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
				return;
			}

			Bukkit.getPluginManager().callEvent(
				new AuthenticationStartEvent(
					validator.isRegistered(),
					event.getPlayer(),
					validator.getUser().getId()
				)
			);
		}
	}
}
