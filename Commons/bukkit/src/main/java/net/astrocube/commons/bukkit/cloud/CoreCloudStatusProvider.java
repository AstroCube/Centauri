package net.astrocube.commons.bukkit.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import org.bukkit.Bukkit;

@Singleton
public class CoreCloudStatusProvider implements CloudStatusProvider {

    @Override
    public boolean hasCloudHooked() {
        return Bukkit.getServer().getPluginManager().getPlugin("TimoCloud") != null;
    }

    @Override
    public int getOnline() {
        return TimoCloudAPI.getUniversalAPI().getPlayers().size();
    }

}
