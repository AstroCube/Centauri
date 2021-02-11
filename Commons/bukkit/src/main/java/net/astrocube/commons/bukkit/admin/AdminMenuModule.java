package net.astrocube.commons.bukkit.admin;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.bukkit.admin.punishment.PunishmentChooserMenu;
import net.astrocube.commons.bukkit.admin.punishment.PunishmentExpirationChooserMenu;
import net.astrocube.commons.bukkit.admin.punishment.PunishmentReasonChooserMenu;
import net.astrocube.commons.bukkit.admin.selector.AdminGameModeSelectorMenu;
import net.astrocube.commons.bukkit.admin.selector.AdminOnlineStaffMenu;
import net.astrocube.commons.bukkit.admin.selector.AdminSubGameModeSelectorMenu;
import net.astrocube.commons.bukkit.admin.selector.item.CoreGameModeItemExtractor;
import net.astrocube.commons.bukkit.admin.selector.item.GameModeItemExtractor;
import net.astrocube.commons.bukkit.admin.selector.item.action.DependentAction;
import net.astrocube.commons.bukkit.admin.selector.item.action.SimpleDependentAction;

public class AdminMenuModule extends ProtectedModule {
    
    @Override
    public void configure() {
        bind(PunishmentChooserMenu.class).in(Scopes.SINGLETON);
        bind(PunishmentReasonChooserMenu.class).in(Scopes.SINGLETON);
        bind(PunishmentExpirationChooserMenu.class).in(Scopes.SINGLETON);
        bind(AdminMainPageMenu.class).in(Scopes.SINGLETON);
        bind(AdminOnlineStaffMenu.class).in(Scopes.SINGLETON);

        bind(AdminMainPageMenu.class).in(Scopes.SINGLETON);
        bind(AdminGameModeSelectorMenu.class).in(Scopes.SINGLETON);
        bind(AdminSubGameModeSelectorMenu.class).in(Scopes.SINGLETON);
        bind(GameModeItemExtractor.class).to(CoreGameModeItemExtractor.class).in(Scopes.SINGLETON);
        bind(DependentAction.class).to(SimpleDependentAction.class).in(Scopes.SINGLETON);
    }
    
}
