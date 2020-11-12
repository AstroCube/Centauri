package net.astrocube.api.bukkit.authentication.server;

import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface CooldownKick {

    /**
     * Check if user has a cooldown and eject him
     * @param user to be kicked
     * @param player to be kicked
     */
    void checkAndKick(User user, Player player) throws AuthorizeException;

    /**
     * Add one try to the cache when invalid authentication performed
     * @param user to be added
     */
    void addTry(User user);

    /**
     * Clear cached tries of user
     * @param user to be cleared
     */
    void clearTries(User user);

    /**
     * Check actual authorization attempts in a 5 minutes interval
     * @param user to be checked
     * @return user interval
     */
    int getTries(User user);

}
