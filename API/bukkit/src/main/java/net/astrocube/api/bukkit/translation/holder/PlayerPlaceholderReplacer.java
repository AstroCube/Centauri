package net.astrocube.api.bukkit.translation.holder;

import me.yushust.message.format.PlaceholderProvider;
import me.yushust.message.track.ContextRepository;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerPlaceholderReplacer implements PlaceholderProvider<Player> {

	@Override
	public @Nullable Object replace(ContextRepository ctx, Player player, String s) {
		if ("player".equals(s)) {
			return player.getName();
		}

		return "";
	}

}