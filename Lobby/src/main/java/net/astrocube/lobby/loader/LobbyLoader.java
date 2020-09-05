package net.astrocube.lobby.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;

import javax.inject.Named;

public class LobbyLoader implements Loader {

    private @Inject @Named("events") Loader loader;

    @Override
    public void load() {
        loader.load();
    }

}
