package net.astrocube.commons.bukkit.game.matchmaking;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Singleton
public class CoreIdealMatchSelector implements IdealMatchSelector {

	@Override
	public Optional<Match> sortAvailableMatches(Set<Match> matches) {
		return matches.stream().max(Comparator.comparingInt(o -> o.getSpectators().size()));
	}

}
