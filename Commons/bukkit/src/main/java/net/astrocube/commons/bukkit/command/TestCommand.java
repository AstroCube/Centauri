package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class TestCommand implements CommandClass {

    private @Inject OnlineStaffProvider onlineStaffProvider;

    @Command(names = {"teststaff"})
    public boolean onCommand() {

        try {
            System.out.println(onlineStaffProvider.provide().size());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

}
