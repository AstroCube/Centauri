package net.astrocube.api.bukkit.authentication.server;

import net.astrocube.api.core.authentication.AuthorizeException;

import java.util.Date;

public interface AuthenticationCooldown {

    /**
     * Will set an cooldown lock for a certain user login
     * @param id of user to be locked
     */
    void setCooldownLock(String id);

    /**
     * Check if user has a cooldown preventing his login
     * @param id of cooldown user
     * @return cooldown status
     */
    boolean hasCooldown(String id);

    /**
     * Obtain the date where cooldown will expire
     * @param id of cooldown user
     * @return remaining time expiration
     */
    Date getRemainingTime(String id);

}
