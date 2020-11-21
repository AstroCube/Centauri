package net.astrocube.commons.bukkit.teleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.teleport.RangeDiscriminator;
import net.astrocube.api.bukkit.teleport.TeleportRequestException;
import net.astrocube.api.bukkit.teleport.UserTeleportDispatcher;
import net.astrocube.api.bukkit.teleport.event.TeleportRequestEvent;
import net.astrocube.api.bukkit.teleport.request.TeleportRange;
import net.astrocube.api.bukkit.teleport.request.TeleportRequest;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;

@Singleton
public class CoreUserTeleportDispatcher implements UserTeleportDispatcher {

    private @Inject RangeDiscriminator rangeDiscriminator;

    @Override
    public void teleport(User requester, User receiver, boolean self) throws TeleportRequestException {

        TeleportRange range = rangeDiscriminator.discriminate(requester, receiver);
        Optional<String> server = Optional.of(""); //TODO: Obtain server when cross-teleport
        Optional<Location> location = Optional.empty();

        if (server.isPresent()) {
            if (self) {
                Player requesterPlayer = Bukkit.getPlayer(requester.getUsername());
                location = Optional.of(requesterPlayer.getLocation());
            } else {
                Player receiverPlayer = Bukkit.getPlayer(receiver.getUsername());
                location = Optional.of(receiverPlayer.getLocation());
            }
        }

        if (!range.isCrossAllowed() && range.isCrossServer()) {
            throw new TeleportRequestException("Cross-teleport not allowed in this server");
        }

        TeleportRequest request = new CoreTeleportRequest(
                range,
                self ? Optional.of(receiver) : Optional.of(requester),
                server,
                location,
                self ? requester : receiver
        );

        Bukkit.getPluginManager().callEvent(new TeleportRequestEvent(request));

    }

}
