package net.astrocube.lobby.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.premium.PremiumConfirmationMenu;
import net.astrocube.api.bukkit.lobby.premium.PremiumSelectBook;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import org.bukkit.entity.Player;

@Command(
	names = "premium"
)
public class PremiumCommand implements CommandClass {

	private @Inject FindService<User> findService;
	private @Inject MessageHandler messageHandler;
	private @Inject PremiumSelectBook premiumSelectBook;
	private @Inject PremiumConfirmationMenu premiumConfirmationMenu;

	@Command(names = {""})
	public boolean onPremiumCommand(@Sender Player player) {

		findService.find(player.getDatabaseIdentifier()).callback(response -> {

			if (!response.isSuccessful()) {
				messageHandler.sendIn(player, AlertModes.ERROR, "premium.error");
			}

			response.ifSuccessful(user -> {

				if (user.getSession().getAuthorizeMethod() == UserDoc.Session.Authorization.PREMIUM) {
					premiumSelectBook.displayDisable(player);
				} else {
					premiumSelectBook.displayEnable(player);
				}

			});

		});

		return true;

	}

	@Command(names = {"confirm"})
	public boolean onPremiumConfirmCommand(@Sender Player player) {
		premiumConfirmationMenu.open(player);
		return true;
	}

}
