package net.astrocube.commons.bukkit.listener.punishment;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.PunishmentKickProcessor;
import net.astrocube.api.bukkit.punishment.event.PunishmentIssueEvent;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.github.paperspigot.Title;

import java.util.logging.Level;

public class PunishmentIssueActionsListener implements Listener {

    private @Inject MessageHandler messageHandler;
    private @Inject FindService<User> findService;
    private @Inject Plugin plugin;
    private @Inject PunishmentKickProcessor punishmentKickProcessor;

    @EventHandler
    public void onPunishmentIssue(PunishmentIssueEvent event) {

        Player player = Bukkit.getPlayerByIdentifier(event.getPunishment().getPunished());

        if (player != null) {

            try {

                User user = findService.findSync(player.getDatabaseIdentifier());

                if (event.getPunishment().getType() == PunishmentDoc.Identity.Type.WARN) {

                    Title title = new Title(
                            messageHandler.get(player, "punish.warn.title"),
                            messageHandler.replacing(
                                    player, "punish.warn.sub",
                                    "%reason%", event.getPunishment().getReason()
                            )
                    );

                    player.sendTitle(title);
                    messageHandler.sendReplacing(
                            player, "punish.warn.chat",
                            "%reason%", event.getPunishment().getReason()
                    );
                    player.playSound(player.getLocation(), Sound.WITHER_DEATH, 1f, 1f);

                    return;
                }

                punishmentKickProcessor.processKick(event.getPunishment(), player, user);

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Error while aplying punishment to player", e);
                player.kickPlayer(ChatColor.RED + "Kicked due to unability to apply punishment. Please re-login.");
            }

        }

    }

}
