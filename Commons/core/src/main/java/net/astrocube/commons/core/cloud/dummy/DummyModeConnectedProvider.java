package net.astrocube.commons.core.cloud.dummy;

import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudModeConnectedProvider;
import net.astrocube.api.core.virtual.gamemode.GameMode;

@Singleton
public class DummyModeConnectedProvider implements CloudModeConnectedProvider {
    @Override
    public int getGlobalOnline(GameMode gameMode) {
        return 0;
    }

    @Override
    public int getGroupOnline(String group) {
        return 0;
    }
}
