package net.astrocube.commons.bukkit.user.profile;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.astrocube.api.bukkit.user.profile.AbstractGameProfile;

public class MojangProfileGeneratorUtil {

    public static GameProfile generateProfile(AbstractGameProfile profile) {

        GameProfile mojangProfile = new GameProfile(profile.getId(), profile.getName());

        profile.getProperties().forEach(
                property -> mojangProfile.getProperties().put(
                        property.getName(), new Property(property.getName(), property.getValue(), property.getSignature())
                )
        );

        return mojangProfile;
    }

}