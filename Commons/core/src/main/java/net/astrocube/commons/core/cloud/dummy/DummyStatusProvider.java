package net.astrocube.commons.core.cloud.dummy;

import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudStatusProvider;

@Singleton
public class DummyStatusProvider implements CloudStatusProvider {
    @Override
    public boolean hasCloudHooked() {
        return false;
    }

    @Override
    public int getOnline() {
        return -1;
    }

    @Override
    public boolean hasAliveSession(String player) {
        return true;
    }

    @Override
    public String getPlayerServer(String player) {
        return "";
    }

    @Override
    public void updateGameStatus(State state) {

    }

}
