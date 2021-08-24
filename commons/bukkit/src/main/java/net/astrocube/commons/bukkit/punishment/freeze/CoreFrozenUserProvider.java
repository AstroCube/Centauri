package net.astrocube.commons.bukkit.punishment.freeze;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.punishment.freeze.FrozenUserProvider;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class CoreFrozenUserProvider implements FrozenUserProvider {

	private final Set<String> registry = new HashSet<>();

	@Override
	public void freeze(Player player) {
		registry.add(player.getDatabaseId());
	}

	@Override
	public void unFreeze(Player player) {
		registry.remove(player.getDatabaseId());
	}

	@Override
	public boolean isFrozen(Player player) {
		return registry.contains(player.getDatabaseId());
	}

}
