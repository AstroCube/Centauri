package net.astrocube.commons.core.virtual;

import me.fixeddev.inject.ProtectedModule;

public class VirtualModule extends ProtectedModule {

	@Override
	protected void configure() {
		install(new FriendshipModelModule());
		install(new GameModeModelModule());
		install(new ServerModelModule());
		install(new UserModelModule());
		install(new PerkModule());
		install(new PunishmentsModelModule());
		install(new GroupModelModule());
	}
}