package net.astrocube.commons.bukkit.channel.admin;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.channel.admin.StaffMessageDelivery;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.core.service.find.FindService;
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
    private @Inject MessageHandler messageHandler;

    @Override
    public void deliver(ChatChannelMessage message) {
        Map<String, Object> meta = message.getMeta();
        boolean important = (boolean) meta.get("important");
        List<String> mentions = (List<String>) meta.get("mentions");

        this.findService.find(message.getSender()).callback(Callbacks.applyCommonErrorHandler("Find userdata of " + message.getSender(),
                sender -> {

                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player.hasPermission("commons.staff.chat")) {
                            this.findService.find(player.getDatabaseIdentifier()).callback(response -> response.ifSuccessful(onlineUser -> {

                                String prefix = displayMatcher.getDisplay(player, sender).getPrefix() + ChatColor.RESET + " " + onlineUser.getDisplay();

                                if (onlineUser.getSettings().getAdminChatSettings().isActive() && !important) {


                                    player.sendMessage(
                                            messageHandler.get(player, "channel.admin.prefix") + " " +
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
                                            messageHandler.get(player, "channel.admin.important")  + " " +
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

        String formatted = message;

        for (String user: mentions) {
            formatted = formatted.replace("@" + user, ChatColor.YELLOW + "@" + user + ChatColor.WHITE);
        }

        return message;
    }
}