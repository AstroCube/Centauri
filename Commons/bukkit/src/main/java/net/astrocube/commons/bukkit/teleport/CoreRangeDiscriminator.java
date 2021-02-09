package net.astrocube.commons.bukkit.teleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.teleport.RangeDiscriminator;
import net.astrocube.api.bukkit.teleport.request.TeleportRange;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.plugin.Plugin;

@Singleton
public class CoreRangeDiscriminator implements RangeDiscriminator {

    private @Inject Plugin plugin;
    private @Inject CloudStatusProvider cloudStatusProvider;

    @Override
    public TeleportRange discriminate(User requester, User receiver) {

        return new TeleportRange() {
            @Override
            public boolean isCrossAllowed() {
                return plugin.getConfig().getBoolean("server.cross-teleport");
            }

            @Override
            public boolean isCrossServer() {

                String requesterServer = cloudStatusProvider.getPlayerServer(requester.getUsername());
                String receiverServer = cloudStatusProvider.getPlayerServer(receiver.getUsername());

                return requesterServer.equalsIgnoreCase(receiverServer);
            }
        };

    }
}
