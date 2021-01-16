package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Named;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class FreezeCommand implements CommandClass {

    @Inject
    private Plugin plugin;

    @Command(names = "freeze")
    public boolean freezeCommand(@Sender Player player,
                                 @Named("target") OfflinePlayer target) {

        if (target == null) {

            //no exists
            return true;
        }

        if (!target.isOnline()) {

            return true;
        }

        target.getPlayer().setMetadata("freeze", new FixedMetadataValue(plugin, player.getName()));
        return true;
    }
}