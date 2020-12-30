package net.astrocube.api.bukkit.game.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;
import net.astrocube.api.core.virtual.gamemode.GameMode;

public interface MapConfigurationProvider {

    /**
     * Parse map configuration processed for a certain match.
     * @param map to be processed
     * @param type of the desired {@link GameMode} configuration which extends {@link GameMapConfiguration}.
     * @return parsed configuration
     * @throws JsonProcessingException
     */
    <T extends GameMapConfiguration> T parseConfiguration(String map, Class<T> type) throws JsonProcessingException;

}
