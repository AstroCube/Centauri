package net.astrocube.commons.bukkit.authentication.radio;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.authentication.radio.AuthenticationRadio;
import net.astrocube.api.bukkit.authentication.radio.AuthenticationSongLoader;

public class AuthenticationRadioModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(AuthenticationSongLoader.class).to(CoreAuthenticationSongLoader.class);
		bind(AuthenticationRadio.class).to(CoreAuthenticationRadio.class);
	}

}
