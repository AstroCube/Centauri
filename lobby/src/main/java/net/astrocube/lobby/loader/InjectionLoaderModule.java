package net.astrocube.lobby.loader;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.translation.TranslationModule;
import net.astrocube.lobby.board.ScoreboardModule;
import net.astrocube.lobby.gamemode.GameModeModule;
import net.astrocube.lobby.hide.HideModule;
import net.astrocube.lobby.hotbar.HotbarModule;
import net.astrocube.lobby.nametag.NametagModule;
import net.astrocube.lobby.premium.PremiumModule;
import net.astrocube.lobby.profile.ProfileModule;
import net.astrocube.lobby.selector.SelectorModule;
import net.astrocube.puppets.entity.PuppetRegistry;
import net.astrocube.puppets.listener.ChunkPuppetListener;
import net.astrocube.puppets.listener.PlayerPuppetListener;
import org.bukkit.plugin.Plugin;

public class InjectionLoaderModule extends ProtectedModule {

	@Override
	public void configure() {
		install(new ScoreboardModule());
		install(new NametagModule());
		install(new TranslationModule());
		install(new GameModeModule());
		install(new SelectorModule());
		install(new PremiumModule());
		install(new HideModule());
		install(new HotbarModule());
		install(new ProfileModule());
	}

	@Provides
	@Singleton
	public ChunkPuppetListener getPuppetChunkListener(PuppetRegistry puppetRegistry) {
		return new ChunkPuppetListener(puppetRegistry);
	}

	@Provides
	@Singleton
	public PlayerPuppetListener getPuppetPlayerListener(PuppetRegistry puppetRegistry, Plugin plugin) {
		return new PlayerPuppetListener(puppetRegistry, plugin);
	}

}
