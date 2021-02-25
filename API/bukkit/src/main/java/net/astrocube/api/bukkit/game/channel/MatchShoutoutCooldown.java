package net.astrocube.api.bukkit.game.channel;

public interface MatchShoutoutCooldown {

    /**
     * Register if player has cooldown
     * @param id of the player
     */
    void registerCooldown(String id);

    /**
     * Check if player has cooldown
     * @param id of the player
     * @return if player has cooldown
     */
    boolean hasCooldown(String id);

}
