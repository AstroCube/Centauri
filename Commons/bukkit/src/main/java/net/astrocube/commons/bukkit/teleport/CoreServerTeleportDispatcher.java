package net.astrocube.commons.bukkit.teleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.teleport.RangeDiscriminator;
import net.astrocube.api.bukkit.teleport.ServerTeleportDispatcher;
import net.astrocube.api.bukkit.teleport.event.TeleportRequestEvent;
import net.astrocube.api.bukkit.teleport.request.TeleportRange;
import net.astrocube.api.bukkit.teleport.request.TeleportRequest;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.Optional;

@Singleton
public class CoreServerTeleportDispatcher implements ServerTeleportDispatcher {

    private @Inject RangeDiscriminator rangeDiscriminator;

    @Override
    public void teleport(User receiver, String server) {
        teleport(receiver, null, server);
    }

    @Override
    public void teleport(User receiver, @Nullable User requester, String server) {

        TeleportRange range = requester == null ?
                new TeleportRange() {
                    @Override
                    public boolean isCrossAllowed() {
                        return true;
                    }

                    @Override
                    public boolean isCrossServer() {
                        return true;
                    }
                } :
                rangeDiscriminator.discriminate(requester, receiver);

        TeleportRequest request = new CoreTeleportRequest(
                range,
                Optional.ofNullable(requester),
                Optional.of(server),
                Optional.empty(),
                receiver
        );

        Bukkit.getPluginManager().callEvent(new TeleportRequestEvent(request));

    }
}
