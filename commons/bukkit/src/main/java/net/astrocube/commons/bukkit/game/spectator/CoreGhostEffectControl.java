package net.astrocube.commons.bukkit.game.spectator;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

@Singleton
public class CoreGhostEffectControl implements GhostEffectControl {

	private final static String TEAM_NAME = "GHOST";

	private Team team;
	private final Set<UUID> spectators = new HashSet<>();

	@Override
	public void createTeam() {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		team = scoreboard.getTeam(TEAM_NAME);

		if (team == null) {
			team = scoreboard.registerNewTeam(TEAM_NAME);
		}

		team.setCanSeeFriendlyInvisibles(true);
		System.out.println("Team " + team.getName() + " created");
	}

	@Override
	public void addPlayer(Player player) {
		if (!spectators.contains(player.getUniqueId())) {
			System.out.println("Add player");
			team.addPlayer(player);

			//player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));
			spectators.add(player.getUniqueId());
		}
	}

	@Override
	public void removePlayer(Player player) {
		if (spectators.contains(player.getUniqueId())) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			team.removePlayer(player);
			spectators.remove(player.getUniqueId());
		}
	}

	@Override
	public boolean isGhost(Player player) {
		return spectators.contains(player.getUniqueId());
	}

}
