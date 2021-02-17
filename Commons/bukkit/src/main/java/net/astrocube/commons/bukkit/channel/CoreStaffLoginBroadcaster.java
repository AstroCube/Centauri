package net.astrocube.commons.bukkit.channel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.channel.admin.StaffLoginBroadcaster;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

@Singleton
public class CoreStaffLoginBroadcaster implements StaffLoginBroadcaster {

    private @Inject FindService<User> findService;
    private @Inject MessageHandler messageHandler;
    private @Inject DisplayMatcher displayMatcher;

    @Override
    public void broadcastLogin(User session, boolean important, boolean connecting) {

        Bukkit.getOnlinePlayers().forEach(player ->
                findService.find(player.getDatabaseIdentifier()).callback(userCallback ->
                        userCallback.ifSuccessful(user -> {

                            String prefix = displayMatcher.getDisplay(player, session).getPrefix()
                                    + ChatColor.WHITE + " " + session.getDisplay();

                            if (user.getSettings().getAdminChatSettings().isReadingLogs()) {

                                messageHandler.sendReplacing(
                                        player, connecting ? "channel.admin.login" : "channel.admin.logout",
                                        "%%prefix%%", messageHandler.get(player, important ? "channel.admin.important" : "channel.admin.prefix"),
                                        "%%player%%", prefix
                                );

                            } else if (important) {

                                messageHandler.sendReplacing(
                                        player, connecting ? "channel.admin.login" : "channel.admin.logout",
                                        "%%prefix%%", messageHandler.get(player, "channel.admin.important"),
                                        "%%player%%", prefix
                                );

                                player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 2f);

                            }

                        })
                )
        );

    }

}
