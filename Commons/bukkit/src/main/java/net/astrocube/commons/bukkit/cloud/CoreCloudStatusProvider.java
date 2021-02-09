package net.astrocube.commons.bukkit.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
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

    @Override
    public boolean hasAliveSession(String player) {
        return TimoCloudAPI.getUniversalAPI().getPlayer(player).isOnline();
    }

    @Override
    public String getPlayerServer(String player) {

        PlayerObject playerObject = TimoCloudAPI.getUniversalAPI().getPlayer(player);

        if (!playerObject.isOnline()) {
            return "";
        }

        return playerObject.getServer().getName();
    }

}
