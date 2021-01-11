package net.astrocube.commons.bukkit.cloud;

import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import org.bukkit.Bukkit;

@Singleton
public class BukkitCloudStatusProvider implements CloudStatusProvider {

    @Override
    public boolean hasCloudHooked() {
        return Bukkit.getServer().getPluginManager().getPlugin("TimoCloud") != null;
    }

}
