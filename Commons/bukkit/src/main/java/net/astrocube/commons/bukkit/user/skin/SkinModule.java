package net.astrocube.commons.bukkit.user.skin;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.user.skin.CustomSkinRegistry;
import net.astrocube.api.bukkit.user.skin.SignedSkinFetcher;

public class SkinModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(CustomSkinRegistry.class).to(CoreCustomSkinRegistry.class);
        bind(SignedSkinFetcher.class).to(CoreSignedSkinFetcher.class);
    }

}
