package net.astrocube.commons.bukkit.game.map;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import net.astrocube.api.bukkit.game.map.GameMapService;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.http.config.HttpClientConfig;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.commons.core.service.CoreModelService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class CoreGameMapService extends CoreModelService<GameMap, GameMap> implements GameMapService {

    private final HttpClientConfig httpClientConfig;

    @Inject
    public CoreGameMapService(
            HttpClientConfig httpClientConfig,
            ExecutorServiceProvider executorServiceProvider
    ) {
        super(new ModelMeta<>(TypeLiteral.get(GameMap.class), TypeLiteral.get(GameMap.class)), executorServiceProvider);
        this.httpClientConfig = httpClientConfig;
    }

    @Override
    public byte[] getMapWorld(String id) throws IOException {
        return getBytesFromFile("file/" + id);
    }

    @Override
    public byte[] getMapConfiguration(String id) throws IOException {
        return getBytesFromFile("config/" + id);
    }

    private byte[] getBytesFromFile(String route) throws IOException {
        BufferedInputStream in = new BufferedInputStream(
                new URL(
                        httpClientConfig.getBaseURL() + this.modelMeta.getRouteKey() + "/" + route
                ).openStream()
        );

        byte[] targetArray = new byte[in.available()];
        in.read(targetArray);
        return targetArray;
    }

}
