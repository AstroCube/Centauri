package net.astrocube.commons.bukkit.menu;

import com.google.inject.Binder;
import com.google.inject.Module;

public class MenuModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(PunishmentChooserMenu.class);
        binder.bind(PunishmentReasonChooserMenu.class);
        binder.bind(PunishmentExpirationChooserMenu.class);
    }
}