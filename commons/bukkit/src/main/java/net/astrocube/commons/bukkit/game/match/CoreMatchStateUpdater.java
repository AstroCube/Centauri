package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.match.MatchStateUpdateEvent;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.update.UpdateService;
import org.bukkit.Bukkit;

@Singleton
public class CoreMatchStateUpdater implements MatchStateUpdater {

	private @Inject UpdateService<Match, MatchDoc.Partial> updateService;

	@Override
	public void updateMatch(Match match, MatchDoc.Status status) throws Exception {
		match.setStatus(status);
		updateService.updateSync(match);
		Bukkit.getPluginManager().callEvent(new MatchStateUpdateEvent(match.getId(), status));
	}

}