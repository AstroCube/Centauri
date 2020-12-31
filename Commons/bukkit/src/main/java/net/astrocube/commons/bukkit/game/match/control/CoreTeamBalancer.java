package net.astrocube.commons.bukkit.game.match.control;

import net.astrocube.api.bukkit.game.match.control.TeamBalancer;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.HashSet;
import java.util.Set;

public class CoreTeamBalancer implements TeamBalancer {
    @Override
    public Set<MatchDoc.Team> balanceTeams(Set<MatchAssignable> assignations) {
        return new HashSet<>();
    }
}
