package net.astrocube.commons.bukkit.teleport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.teleport.request.TeleportRange;
import net.astrocube.api.bukkit.teleport.request.TeleportRequest;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Location;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class CoreTeleportRequest implements TeleportRequest {

    private final TeleportRange range;
    private final Optional<User> requester;
    private final Optional<String> server;
    private final Optional<Location> location;
    private final User receiver;

}
