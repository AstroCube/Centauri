package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.api.bukkit.authentication.gateway.RegisterGatewayProcessor;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandClass {

    private @Inject RegisterGatewayProcessor registerGatewayProcessor;

    @Command(names = {"register"})
    public boolean onRegister(@Sender Player player, String password) {
        registerGatewayProcessor.validateRegister(player, password);
        return true;
    }

}
