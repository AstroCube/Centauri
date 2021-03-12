package net.astrocube.commons.bukkit.perk;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.perk.PerkManifestProvider;

public class PerkModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(PerkManifestProvider.class).to(CorePerkManifestProvider.class);
    }

}
