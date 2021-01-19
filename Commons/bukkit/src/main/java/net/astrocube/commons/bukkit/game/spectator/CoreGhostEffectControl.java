package net.astrocube.commons.bukkit.game.spectator;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

@Singleton
public class CoreGhostEffectControl implements GhostEffectControl {

    @Override
    public void addPlayer(String match, Player player) {

        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(match);

        if (team == null) {
            team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(match);
            team.setCanSeeFriendlyInvisibles(true);
        }

        team.addPlayer(player);

    }

    @Override
    public void removePlayer(String match, Player player) {

        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam( match);

        if (team == null) {
            return;
        }

        team.getPlayers().remove(player);

    }

    @Override
    public void clearMatch(String match) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(match);
        team.unregister();
    }


}
