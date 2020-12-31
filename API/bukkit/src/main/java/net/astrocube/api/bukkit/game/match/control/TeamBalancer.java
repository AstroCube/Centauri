package net.astrocube.api.bukkit.game.match.control;

import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.Set;

public interface TeamBalancer {

    Set<MatchDoc.Team> balanceTeams(Set<MatchAssignable> assignations);

}
