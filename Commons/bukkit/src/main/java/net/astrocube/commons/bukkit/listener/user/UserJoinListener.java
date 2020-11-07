package net.astrocube.commons.bukkit.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.api.bukkit.teleport.CrossTeleportExchanger;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.permission.PermissionBalancer;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.session.SessionAliveInterceptor;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.session.registry.SessionRegistry;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.permission.CorePermissible;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.logging.Level;

public class UserJoinListener implements Listener {

    private @Inject SessionService sessionService;
    private @Inject FindService<User> userFindService;
    private @Inject PermissionBalancer permissionBalancer;
    private @Inject SessionAliveInterceptor sessionAliveInterceptor;
    private @Inject UserMatchJoiner userMatchJoiner;
    private @Inject CrossTeleportExchanger crossTeleportExchanger;
    private @Inject Plugin plugin;

    private static Field playerField;

    static {
        try {
            playerField = CraftHumanEntity.class.getDeclaredField("perm");
            playerField.setAccessible(true);
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "[Commons] Internal error where obtaining reflection field.");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUserJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        ServerDoc.Type type = ServerDoc.Type.valueOf(plugin.getConfig().getString("server.type"));

        this.userFindService.find(player.getDatabaseIdentifier()).callback(response -> {
            try {

                if (!response.isSuccessful() || !response.getResponse().isPresent())
                    throw new Exception("User response was not present");

                User user = response.getResponse().get();
                String address = Bukkit.getServerName().split("-")[0];

                sessionService.serverSwitch(() -> new SessionValidateDoc.ServerSwitch() {
                    @Override
                    public String getUser() {
                        return user.getId();
                    }

                    @Override
                    public String getServer() {
                        return address;
                    }

                    @Nullable
                    @Override
                    public String getLobby() {
                        return null; //TODO: Get lobby name
                    }
                });

                playerField.set(player, new CorePermissible(player, userFindService, permissionBalancer));

                if (
                        !plugin.getConfig().getBoolean("authentication.enabled") &&
                        !plugin.getConfig().getBoolean("server.sandbox")
                ) {

                    Optional<SessionRegistry> registryOptional = sessionAliveInterceptor.isAlive(user.getId());

                    if (!registryOptional.isPresent()) throw new AuthorizeException("Not authorized session");

                    SessionRegistry registry = registryOptional.get();

                    if (registry.isPending()) throw new AuthorizeException("Session is pending of authorization");

                    if (!registry.getAddress().equalsIgnoreCase(address))
                        throw new AuthorizeException("Matching address not correspond to authorization");

                    crossTeleportExchanger.exchange(user);

                }

                if (type == ServerDoc.Type.LOBBY) {
                    Bukkit.getPluginManager().callEvent(new LobbyJoinEvent(player, user));
                } else if (type == ServerDoc.Type.GAME) {
                    userMatchJoiner.processJoin(user, player);
                }

            } catch (Exception exception) {

                String append = exception instanceof AuthorizeException ?
                        "\n\n" + ChatColor.GRAY + "(Reason: " + exception.getMessage() + ")" : "";

                Bukkit.getScheduler().runTask(plugin, () ->
                        player.kickPlayer(ChatColor.RED + "There was an error processing your login. Please try again later." + append));
                plugin.getLogger().log(Level.SEVERE, "Could not process player final join.", exception);
            }
        });
    }
}
