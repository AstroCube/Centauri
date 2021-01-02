package net.astrocube.commons.bukkit.game.match.control;

import net.astrocube.api.bukkit.game.match.control.TeamBalancer;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.*;

public class CoreTeamBalancer implements TeamBalancer {

    @Override
    public Set<MatchDoc.Team> balanceTeams(Set<MatchAssignable> assignations) {
        /*
        List<MatchDoc.Team> teams = new ArrayList<>();
        List<MatchDoc.Team> finalTeams = new ArrayList<>();

        int maxMembers = 1;
        int teamIndex = 0;
        int partyMembersCount = 0;

        for (MatchAssignable assignable : assignations) {

            Set<String> members = new HashSet<>(assignable.getInvolved());
            members.add(assignable.getResponsible());

            if (members.size() <= maxMembers) {

                MatchDoc.Team team = teams.get(teamIndex);

                int teamMembersCount = team.getMembers().size() + members.size();

                if (teamMembersCount <= maxMembers) {
                    members.forEach(member -> team.getMembers().add(generateMember(member)));

                    if (teamMembersCount == maxMembers) {
                        teamIndex++;
                    }

                    partyMembersCount += members.size();

                }

                finalTeams.add(team);

            }

        }
         */
        return new HashSet<>();
    }

    private MatchDoc.TeamMember generateMember(String id) {
        return new MatchDoc.TeamMember() {
            @Override
            public String getUser() {
                return id;
            }

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public Date getJoinedAt() {
                return new Date();
            }
        };
    }

}
