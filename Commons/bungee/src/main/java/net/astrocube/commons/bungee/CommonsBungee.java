package net.astrocube.commons.bungee;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bungee.loader.InjectionModule;
import net.md_5.bungee.api.plugin.Plugin;

public class CommonsBungee extends Plugin {

    private @Inject Loader loader;

    @Override
    public void onEnable() {
        loader.load();
    }

    @Override
    public void configure(ProtectedBinder binder) {
        binder.install(new InjectionModule());
    }

}
