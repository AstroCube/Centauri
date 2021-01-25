package net.astrocube.commons.bungee.cloud;

import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.md_5.bungee.api.ProxyServer;

@Singleton
public class CoreCloudStatusProvider implements CloudStatusProvider {

    @Override
    public boolean hasCloudHooked() {
        return ProxyServer.getInstance().getPluginManager().getPlugin("TimoCloud") != null;
    }

}
