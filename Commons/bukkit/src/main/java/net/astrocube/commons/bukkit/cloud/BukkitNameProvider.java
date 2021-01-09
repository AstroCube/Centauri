package net.astrocube.commons.bukkit.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import org.bukkit.Bukkit;

@Singleton
public class BukkitNameProvider implements InstanceNameProvider {

    private boolean verified = false;
    private String name = "";

    @Override
    public String getName() {

        if (!verified) {

            this.verified = true;

            if (Bukkit.getServer().getPluginManager().getPlugin("TimoCloud") != null) {
                this.name = TimoCloudAPI.getBukkitAPI().getThisServer().getName();
            } else {
                this.name = Bukkit.getServerName();
            }

        }

        return name;
    }
}
