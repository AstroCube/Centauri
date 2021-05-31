package net.astrocube.commons.bukkit.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.inventory.PlayerHotbarClickListener;
import net.astrocube.commons.bukkit.listener.user.*;
import org.bukkit.plugin.Plugin;

public class UserListenerLoader implements ListenerLoader {

	private @Inject Plugin plugin;

	private @Inject UserPreLoginListener userPreLoginListener;
	private @Inject UserLoginListener userLoginListener;
	private @Inject UserJoinListener userJoinListener;
	private @Inject UserDisconnectListener userDisconnectListener;
	private @Inject PlayerHotbarClickListener playerHotbarClickListener;
	private @Inject UserChatListener userChatListener;

	@Override
	public void registerEvents() {
		registerEvent(
			plugin,
			userPreLoginListener,
			userLoginListener,
			userJoinListener,
			userDisconnectListener,
			playerHotbarClickListener,
			userChatListener
		);
	}
}
