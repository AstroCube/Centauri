package net.astrocube.commons.bukkit.user.skin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.astrocube.api.bukkit.user.profile.AbstractProperty;
import net.astrocube.api.bukkit.user.skin.CustomSkinRegistry;
import net.astrocube.api.bukkit.user.skin.SignedSkinFetcher;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@Singleton
public class CoreCustomSkinRegistry implements CustomSkinRegistry {

    private @Inject SignedSkinFetcher skinFetcher;

    @Override
    public void add(Player player, String skin) {

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        GameProfile profile = entityPlayer.getProfile();
        AbstractProperty property = skinFetcher.fetch(skin);
        PropertyMap properties = profile.getProperties();

        properties.removeAll("textures");
        properties.put("textures", new Property(
                property.getName(),
                property.getValue(),
                property.getSignature()
        ));

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.hidePlayer(player);
            online.showPlayer(player);
        }

    }

}
