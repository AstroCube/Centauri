package net.astrocube.lobby.premium;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.premium.PremiumConfirmationMenu;
import net.astrocube.api.bukkit.lobby.premium.PremiumSelectBook;
import net.astrocube.api.bukkit.lobby.premium.PremiumStatusSwitcher;

public class PremiumModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(PremiumConfirmationMenu.class).to(CorePremiumConfirmationMenu.class);
        bind(PremiumSelectBook.class).to(CorePremiumSelectBook.class);
        bind(PremiumStatusSwitcher.class).to(CorePremiumStatusSwitcher.class);
    }

}
