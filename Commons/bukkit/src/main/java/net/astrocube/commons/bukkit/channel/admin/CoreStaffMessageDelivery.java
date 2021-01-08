package net.astrocube.commons.bukkit.channel.admin;

import net.astrocube.api.bukkit.channel.admin.StaffMessageDelivery;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.FlairFormatter;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class CoreStaffMessageDelivery implements StaffMessageDelivery {

    private @Inject FindService<User> findService;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject FlairFormatter flairFormatter;

    @Override
    public void deliver(ChatChannelMessage message) {
        Map<String, Object> meta = message.getMeta();
        boolean important = (boolean) meta.get("important");
        List<String> mentions = (List<String>) meta.get("mentions");

        this.findService.find(message.getSender()).callback(Callbacks.applyCommonErrorHandler("Find userdata of " + message.getSender(),
                sender -> {
                    Group.Flair flair = this.displayMatcher.getRealmDisplay(sender);
                    String prefix = this.flairFormatter.format(flair, sender.getDisplay());

                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player.hasPermission("commons.staff.chat")) {
                            this.findService.find(player.getDatabaseIdentifier()).callback(response -> response.ifSuccessful(onlineUser -> {

                                if (onlineUser.getSettings().getAdminChatSettings().isActive() && !important) {

                                    player.sendMessage(
                                            ChatColor.AQUA + "[STAFF] " +
                                                    prefix + ChatColor.WHITE + ": " +
                                                    this.formatMessage(message.getMessage(), mentions)
                                    );

                                    mentions.forEach(mention -> {
                                        if (mention.equalsIgnoreCase(onlineUser.getDisplay())) {
                                            player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
                                        }
                                    });
                                } else if (important) {
                                    player.sendMessage(
                                            ChatColor.RED + "[STAFF] " +
                                                    prefix + ChatColor.WHITE + ": " +
                                                    this.formatMessage(message.getMessage(), mentions)
                                    );
                                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                                }
                            }));
                        }
                    });
                })
        );
    }

    private String formatMessage(String message, List<String> mentions) {
        for (String user: mentions)
            message = message.replace("@" + user, ChatColor.YELLOW + "@" + user + ChatColor.WHITE);
        return message;
    }
}