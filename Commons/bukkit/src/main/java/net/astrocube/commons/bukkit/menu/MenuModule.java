package net.astrocube.commons.bukkit.menu;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MenuModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(PunishmentChooserMenu.class).in(Scopes.SINGLETON);
        binder.bind(PunishmentReasonChooserMenu.class).in(Scopes.SINGLETON);
        binder.bind(PunishmentReasonChooserMenu.class).in(Scopes.SINGLETON);
    }
}