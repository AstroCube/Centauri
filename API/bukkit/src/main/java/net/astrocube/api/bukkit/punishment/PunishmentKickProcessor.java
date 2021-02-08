package net.astrocube.api.bukkit.punishment;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface PunishmentKickProcessor {

    /**
     * Process a global kick of a player who has a punishment.
     * @param punishment to be checked.
     * @param player to use
     * @param user where language will be taken
     */
    void processKick(Punishment punishment, Player player, User user) throws JsonProcessingException;

    /**
     * Check if player needs to be kicked for a certain punishment
     * @param player to use
     * @param user where language will be taken.
     */
    void validateKick(Player player, User user) throws Exception;

}
