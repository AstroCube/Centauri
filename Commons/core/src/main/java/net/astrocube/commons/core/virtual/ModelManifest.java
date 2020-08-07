package net.astrocube.commons.core.virtual;

import me.fixeddev.inject.ProtectedModule;

public class ModelManifest extends ProtectedModule {

    @Override
    protected void configure() {
        install(new FriendshipModelManifest());
        install(new GameModeModelManifest());
        install(new ServerModelManifest());
    }

}
