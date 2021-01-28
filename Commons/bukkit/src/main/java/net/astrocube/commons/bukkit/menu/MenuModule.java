package net.astrocube.commons.bukkit.menu;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import net.astrocube.commons.bukkit.menu.admin.AdminMainPageMenu;
import net.astrocube.commons.bukkit.menu.admin.selector.AdminGameModeSelectorMenu;
import net.astrocube.commons.bukkit.menu.admin.selector.AdminOnlineStaffMenu;
import net.astrocube.commons.bukkit.menu.admin.selector.AdminSubGameModeSelectorMenu;
import net.astrocube.commons.bukkit.menu.admin.selector.item.CoreGameModeItemExtractor;
import net.astrocube.commons.bukkit.menu.admin.selector.item.GameModeItemExtractor;
import net.astrocube.commons.bukkit.menu.admin.selector.item.action.DependentAction;
import net.astrocube.commons.bukkit.menu.admin.selector.item.action.SimpleDependentAction;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentChooserMenu;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentExpirationChooserMenu;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentReasonChooserMenu;

public class MenuModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(PunishmentChooserMenu.class).in(Scopes.SINGLETON);
        binder.bind(PunishmentReasonChooserMenu.class).in(Scopes.SINGLETON);
        binder.bind(PunishmentExpirationChooserMenu.class).in(Scopes.SINGLETON);
        binder.bind(AdminMainPageMenu.class).in(Scopes.SINGLETON);
        binder.bind(AdminOnlineStaffMenu.class).in(Scopes.SINGLETON);

        binder.bind(AdminMainPageMenu.class).in(Scopes.SINGLETON);
        binder.bind(AdminGameModeSelectorMenu.class).in(Scopes.SINGLETON);
        binder.bind(AdminSubGameModeSelectorMenu.class).in(Scopes.SINGLETON);
        binder.bind(GameModeItemExtractor.class).to(CoreGameModeItemExtractor.class).in(Scopes.SINGLETON);
        binder.bind(DependentAction.class).to(SimpleDependentAction.class).in(Scopes.SINGLETON);
    }
}