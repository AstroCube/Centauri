package net.astrocube.commons.bukkit.listener.punishment;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.event.PunishmentIssueEvent;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PunishmentStaffChatListener implements Listener {

    private @Inject FindService<User> findService;
    private @Inject DisplayMatcher matcher;
    private @Inject MessageHandler messageHandler;

    @EventHandler
    public void onPunishmentIssue(PunishmentIssueEvent event) {

        if (!event.getPunishment().isSilent()) {

            if (event.getPunishment().isAutomatic()) {
                executeFixedPunishment(event.getPunishment(), null);
            }

            findService.find(event.getPunishment().getIssuer()).callback(issuerResponse -> {
                issuerResponse.ifSuccessful(issuer -> executeFixedPunishment(event.getPunishment(), issuer));
            });

        }

    }

    private void executeFixedPunishment(Punishment punishment, User issuer) {
        findService.find(punishment.getPunished()).callback(punishedResponse ->
                punishedResponse.ifSuccessful(punished ->
                        Bukkit.getOnlinePlayers().forEach(player -> {

                            if (player.hasPermission("commons.staff.chat") && !player.getDatabaseIdentifier().equalsIgnoreCase(punished.getId())) {

                                findService.find(player.getDatabaseIdentifier()).callback(playerResponse -> {

                                    String issuerPrefix = issuer != null ? matcher.getDisplay(player, issuer).getColor()
                                            + issuer.getDisplay() : messageHandler.get(player, "channel.admin.auto");

                                    String punishedPrefix = matcher.getDisplay(player, punished).getColor() +
                                            punished.getDisplay();

                                    playerResponse.ifSuccessful(user -> {

                                        if (user.getSettings().getAdminChatSettings().isReadingPunishments()) {

                                            messageHandler.sendReplacing(
                                                    player, "channel.admin.punish",
                                                    "%prefix%", messageHandler.get(player, "channel.admin.prefix"),
                                                    "%type%", messageHandler.get(player, "punish-menu.type." + punishment.getType().toString().toLowerCase()),
                                                    "%issuer%", issuerPrefix,
                                                    "%punished%", punishedPrefix
                                            );

                                        }

                                    });

                                });

                            }

                        })
                )
        );
    }

}
