package net.astrocube.commons.bukkit.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class LobbyCommand implements CommandClass {

    private @Inject Plugin plugin;
    private @Inject ServerTeleportRetry serverTeleportRetry;

    @Command(names = {"hub", "lobby", "l"})
    public void onCommandPerform(@Sender Player player) {

        serverTeleportRetry.attemptGroupTeleport(
                player.getName(),
                plugin.getConfig().getString("server.fallback"),
                1,
                3
        );

    }

}
