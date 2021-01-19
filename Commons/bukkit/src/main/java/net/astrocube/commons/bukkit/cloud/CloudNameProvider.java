package net.astrocube.commons.bukkit.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import org.bukkit.Bukkit;

import java.util.Date;

@Singleton
public class CloudNameProvider implements InstanceNameProvider {

    private final CloudStatusProvider cloudStatusProvider;
    private boolean verified;
    private String name;

    @Inject
    public CloudNameProvider(CloudStatusProvider cloudStatusProvider) {
        this.cloudStatusProvider = cloudStatusProvider;
        this.verified = false;
        this.name = "";
    }

    @Override
    public String getName() {

        if (!verified) {

            this.verified = true;

            if (cloudStatusProvider.hasCloudHooked()) {
                this.name = TimoCloudAPI.getBukkitAPI().getThisServer().getId();
            } else {
                this.name = Bukkit.getServerName();
            }

        }

        return name;
    }
}
