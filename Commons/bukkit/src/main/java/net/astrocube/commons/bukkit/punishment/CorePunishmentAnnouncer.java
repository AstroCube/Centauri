package net.astrocube.commons.bukkit.punishment;

import com.google.inject.Inject;

import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.punishment.PunishmentAnnouncer;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CorePunishmentAnnouncer implements PunishmentAnnouncer {

    private @Inject FindService<User> findService;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject MessageHandler<Player> messageHandler;

    @Override
    public void alertWarn(Player issuer, Player punished, String reason) {
        messageHandler.sendMessage(issuer, "punishments.warn.issuer"
                .replace("%%receiver%%", getFullPrefix(punished))
                .replace("%%reason%%", reason)
        );

        if (punished == null) {
            return;
        }

        messageHandler.getMessages(punished, "punishments.warn.punished")
                .forEach(s -> punished.sendMessage(s.replace("%%reason%%", reason)));
    }

    @Override
    public void alertBan(Player issuer, Player punished, String reason, String duration) {

    }

    @Override
    public void alertKick(Player issuer, Player punished, String reason) {

    }

    private String getFullPrefix(Player player) {
        try {
            User userPunished = findService.findSync(player.getDatabaseIdentifier());

            Group.Flair userPunishedFlair = displayMatcher.getRealmDisplay(userPunished);

            return userPunishedFlair.getColor() + userPunishedFlair.getSymbol() + player.getDisplayName();
        } catch (Exception e) {
            return ChatColor.GRAY + player.getDisplayName();
        }
    }

}