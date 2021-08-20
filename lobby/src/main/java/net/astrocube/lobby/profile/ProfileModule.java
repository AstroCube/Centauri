package net.astrocube.lobby.profile;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.profile.UserProfileDisplay;

public class ProfileModule extends ProtectedModule {

	@Override
	protected void configure() {
		bind(UserProfileDisplay.class).to(CoreUserProfileDisplay.class);
	}
}
