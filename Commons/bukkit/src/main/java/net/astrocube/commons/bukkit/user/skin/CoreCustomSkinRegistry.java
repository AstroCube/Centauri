package net.astrocube.commons.bukkit.user.skin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.astrocube.api.bukkit.user.profile.AbstractProperty;
import net.astrocube.api.bukkit.user.skin.CustomSkinRegistry;
import net.astrocube.api.bukkit.user.skin.SignedSkinFetcher;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

@Singleton
public class CoreCustomSkinRegistry implements CustomSkinRegistry {

    private @Inject SignedSkinFetcher skinFetcher;
    private @Inject Plugin plugin;
    private Location location = null;

    @Override
    public void add(Player player, String skin) {

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        if (location == null) {

            World world = new WorldCreator("skinFetching").createWorld();
            location = new Location(world, 0, 0, 0);

        }

        GameProfile profile = entityPlayer.getProfile();
        AbstractProperty property = skinFetcher.fetch(skin);

        profile.getProperties().put(
                "texture",
                new Property(property.getName(), property.getValue(), property.getSignature())
        );

        Bukkit.getScheduler().runTask(plugin, () -> {
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.hidePlayer(player);
                online.showPlayer(player);
            }
        });

    }

}
