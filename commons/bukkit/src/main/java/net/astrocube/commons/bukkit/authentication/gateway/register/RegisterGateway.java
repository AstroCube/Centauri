package net.astrocube.commons.bukkit.authentication.gateway.register;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@Singleton
public class RegisterGateway implements AuthenticationGateway {

	private @Inject MessageHandler messageHandler;

	@Override
	public void startProcessing(User user) {

		@Nullable Player player = Bukkit.getPlayer(user.getUsername());

		if (player != null) {
			player.sendTitle(
				messageHandler.get(player, "authentication.register-title"),
				messageHandler.get(player, "authentication.register-sub"),
				10,
				40,
				10
			);
		}
	}

	@Override
	public String getName() {
		return "Registration";
	}
}
