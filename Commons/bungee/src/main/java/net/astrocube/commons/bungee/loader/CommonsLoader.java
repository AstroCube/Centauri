package net.astrocube.commons.bungee.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.astrocube.api.core.loader.Loader;


@Singleton
public class CommonsLoader implements Loader {

    private @Inject @Named("server") Loader serverLoader;
    private @Inject @Named("config") Loader configLoader;

    @Override
    public void load() {
        configLoader.load();
        serverLoader.load();
    }

}

