package net.astrocube.commons.bukkit.game.spectator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.delete.DeleteService;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreGhostEffectControl implements GhostEffectControl {

	@Inject
	private DeleteService<Match> deleteService;

	private final Map<String, String> teamAssignation = new HashMap<>();

	@Override
	public void addPlayer(String match, Player player) {

		String assigned = teamAssignation.get(match);

		Team team = assigned == null ? createTeam(match) :
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam(assigned);

		team.addPlayer(player);
	}

	private Team createTeam(String match) {
		String id = "s_" + RandomStringUtils.random(8);
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(id);
		teamAssignation.put(match, id);
		team.setCanSeeFriendlyInvisibles(true);
		return team;
	}

	@Override
	public void removePlayer(String match, Player player) {

		String assigned = teamAssignation.get(match);

		if (assigned == null) {
			return;
		}

		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(assigned);

		if (team == null) {
			return;
		}

		team.getPlayers().remove(player);

	}

	@Override
	public void clearMatch(String match) {

		String assigned = teamAssignation.get(match);

		if (assigned == null) {
			return;
		}

		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(assigned);
		team.unregister();
		teamAssignation.remove(match);
		deleteService.delete(match);

	}

}
