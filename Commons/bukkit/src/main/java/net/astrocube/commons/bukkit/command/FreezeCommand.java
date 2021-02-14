package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class FreezeCommand implements CommandClass {

    private @Inject Plugin plugin;
    private @Inject QueryService<User> queryService;
    private @Inject MessageHandler messageHandler;

    @Command(names = "freeze", permission = "commons.staff.freeze")
    public boolean freezeCommand(@Sender Player player, OfflinePlayer target) {

        if (target == null || !target.isOnline()) {
            messageHandler.sendIn(player, AlertModes.ERROR, "commands.unknown-player");
            return true;
        }

        return true;
    }
}