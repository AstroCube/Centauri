package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.api.bukkit.authentication.gateway.PasswordGatewayProcessor;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandClass {

	private @Inject PasswordGatewayProcessor passwordGatewayProcessor;

	@Command(names = {"login"})
	public boolean onLogin(@Sender Player player, String password) {
		passwordGatewayProcessor.validateLogin(player, password);
		return true;
	}

}
