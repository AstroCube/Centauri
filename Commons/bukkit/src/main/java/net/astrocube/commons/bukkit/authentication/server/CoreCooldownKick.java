package net.astrocube.commons.bukkit.authentication.server;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.bukkit.authentication.server.CooldownKick;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Singleton
public class CoreCooldownKick implements CooldownKick {

    private final AuthenticationCooldown authenticationCooldown;
    private final MessageProvider<Player> messageProvider;
    private final Plugin plugin;
    private final Cache<String, Integer> userTries;

    @Inject
    public CoreCooldownKick(
            AuthenticationCooldown authenticationCooldown,
            MessageProvider<Player> messageProvider,
            Plugin plugin
    ) {
        this.authenticationCooldown = authenticationCooldown;
        this.messageProvider = messageProvider;
        this.plugin = plugin;
        this.userTries = CacheBuilder
                .newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .weakValues()
                .build();
    }

    @Override
    public void checkAndKick(User user, Player player) throws AuthorizeException {
        if (authenticationCooldown.hasCooldown(user.getId())) {
            Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(
                    ChatColor.RED + messageProvider.getMessage(player, "authentication.attempts")
            ));
        }
    }

    @Override
    public void addTry(User user) {

        if (userTries.getIfPresent(user.getId()) == null) {
            userTries.put(user.getId(), 1);
            return;
        }

        userTries.put(user.getId(), Objects.requireNonNull(userTries.getIfPresent(user.getId())));
    }

    @Override
    public void clearTries(User user) {
        userTries.invalidate(user.getId());
    }

}
