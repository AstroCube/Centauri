package net.astrocube.api.bukkit.teleport;

public class TeleportRequestException extends Exception {

    public TeleportRequestException(String message) {
        super(message);
    }

    enum Cause {
        RANGE, OFFLINE
    }
}
