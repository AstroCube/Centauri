package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import net.astrocube.api.bukkit.authentication.gateway.RegisterGatewayProcessor;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandClass {

    private @Inject RegisterGatewayProcessor registerGatewayProcessor;

    @ACommand(names = {"login"})
    public boolean onLogin(@Injected(true) @Sender Player player, String password) {
        registerGatewayProcessor.validateRegister(player, password);
        return true;
    }

}
