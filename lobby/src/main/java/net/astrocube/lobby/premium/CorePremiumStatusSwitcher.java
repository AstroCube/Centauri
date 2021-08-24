package net.astrocube.lobby.premium;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.premium.PremiumStatusSwitcher;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import org.bukkit.entity.Player;

public class CorePremiumStatusSwitcher implements PremiumStatusSwitcher {

	private @Inject FindService<User> findService;
	private @Inject UpdateService<User, UserDoc.Partial> updateService;
	private @Inject MessageHandler messageHandler;

	@Override
	public void switchStatus(Player player) {

		findService.find(player.getDatabaseId()).callback(response -> {

			if (!response.isSuccessful()) {
				messageHandler.sendIn(player, AlertModes.ERROR, "premium.error");
			}

			response.ifSuccessful(user -> {

				if (user.getSession().getAuthorizeMethod() == UserDoc.Session.Authorization.PREMIUM) {
					user.getSession().setAuthorizeMethod(UserDoc.Session.Authorization.PASSWORD);
				} else {
					user.getSession().setAuthorizeMethod(UserDoc.Session.Authorization.PREMIUM);
				}

				updateService.update(user).callback(updateResponse -> {

					if (!response.isSuccessful()) {
						messageHandler.sendIn(player, AlertModes.ERROR, "premium.error");
					}

					updateResponse.ifSuccessful(update -> {

						if (user.getSession().getAuthorizeMethod() == UserDoc.Session.Authorization.PREMIUM) {
							messageHandler.sendIn(player, AlertModes.MUTED, "premium.enable.alert");
						} else {
							messageHandler.sendIn(player, AlertModes.MUTED, "premium.disable.alert");
						}

					});

				});
			});

		});

	}

}
