package net.astrocube.api.bukkit.punishment;

import net.astrocube.api.core.virtual.punishment.PunishmentDoc;

public interface PresetPunishment {

    /**
     * @return id registered at configuration
     */
    String getId();

    /**
     * @return type of the punishment
     */
    PunishmentDoc.Identity.Type getType();

    /**
     * @return expiration in milis of the punishment (-1 for permanent)
     */
    long getExpiration();

}
