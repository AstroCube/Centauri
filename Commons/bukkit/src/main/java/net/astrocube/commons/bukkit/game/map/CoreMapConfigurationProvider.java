package net.astrocube.commons.bukkit.game.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;

@Singleton
public class CoreMapConfigurationProvider implements MapConfigurationProvider {

    private @Inject ObjectMapper mapper;

    @Override
    public <T extends GameMapConfiguration> T parseConfiguration(String map, Class<T> type) throws JsonProcessingException {
        return mapper.readValue(map, type);
    }
}
