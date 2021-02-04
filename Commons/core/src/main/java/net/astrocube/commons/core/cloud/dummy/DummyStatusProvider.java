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
}
