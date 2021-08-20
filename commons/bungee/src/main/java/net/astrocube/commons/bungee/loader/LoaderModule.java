package net.astrocube.commons.bungee.loader;

import com.google.inject.name.Names;
import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.loader.Loader;

public class LoaderModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(Loader.class).to(CommonsLoader.class);
		bind(Loader.class).annotatedWith(Names.named("config")).to(ConfigurationLoader.class);
		bind(Loader.class).annotatedWith(Names.named("server")).to(ServerLoader.class);
		bind(Loader.class).annotatedWith(Names.named("listener")).to(ListenerLoader.class);
	}

}
