package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.freeze.FreezeRequestAlerter;
import net.astrocube.api.bukkit.punishment.freeze.FrozenUserProvider;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandClass {

    private @Inject MessageHandler messageHandler;
    private @Inject FrozenUserProvider frozenUserProvider;
    private @Inject FreezeRequestAlerter freezeRequestAlerter;

    @Command(names = "freeze", permission = "commons.staff.freeze")
    public boolean freezeCommand(@Sender Player player, OfflinePlayer target) {

        if (checkFreezingAvailability(player, target)) {
            return true;
        }

        if (frozenUserProvider.isFrozen(target.getPlayer())) {
            messageHandler.sendIn(player, AlertModes.ERROR, "freeze.already-frozen");
            return true;
        }

        frozenUserProvider.freeze(target.getPlayer());
        freezeRequestAlerter.alert(target.getPlayer());

        return true;
    }

    @Command(names = "unfreeze", permission = "commons.staff.freeze")
    public boolean unFreezeCommand(@Sender Player player, OfflinePlayer target) {

        if (checkFreezingAvailability(player, target)) {
            return true;
        }

        if (!frozenUserProvider.isFrozen(target.getPlayer())) {
            messageHandler.sendIn(player, AlertModes.ERROR, "freeze.not-frozen");
            return true;
        }

        frozenUserProvider.unFreeze(target.getPlayer());
        freezeRequestAlerter.alertUnfreeze(target.getPlayer());

        return true;
    }

    private boolean checkFreezingAvailability(@Sender Player player, OfflinePlayer target) {

        if (target == null || !target.isOnline()) {
            messageHandler.sendIn(player, AlertModes.ERROR, "commands.unknown-player");
            return true;
        }

        if (player.getName().equalsIgnoreCase(target.getName())) {
            messageHandler.sendIn(player, AlertModes.ERROR, "punish.freeze.same");
            return true;
        }

        return false;
    }

}