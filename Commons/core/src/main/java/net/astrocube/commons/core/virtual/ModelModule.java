package net.astrocube.commons.core.virtual;


import net.astrocube.commons.core.inject.ProtectedModule;

public class ModelModule extends ProtectedModule {

    @Override
    protected void configure() {
        install(new FriendshipModelModule());
        install(new GameModeModelModule());
        install(new ServerModelModule());
        install(new UserModelModule());
    }

}
