package net.astrocube.commons.bukkit.game.match.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;
import net.astrocube.api.bukkit.game.map.configuration.SizedTeam;
import net.astrocube.api.bukkit.game.match.control.TeamBalancer;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;

import java.util.*;
import java.util.stream.Collectors;

public class CoreTeamBalancer implements TeamBalancer {

    private @Inject GameMapCache gameMapCache;
    private @Inject MapConfigurationProvider mapConfigurationProvider;
    private @Inject FindService<GameMode> findService;

    @Override
    public Set<MatchDoc.Team> balanceTeams(Match match, Set<MatchAssignable> assignations) throws GameControlException, JsonProcessingException {

        Optional<Integer> maxPerTeam = getMaxMembers(match);

        if (!maxPerTeam.isPresent()) {
            throw new GameControlException("Not maximum players can be parsed");
        }

        List<SizedTeam> teams = mapConfigurationProvider.parseConfiguration(
                new String(gameMapCache.getConfiguration(match.getMap())),
                GameMapConfiguration.class
        )
                .getTeams()
                .stream()
                .map(mapTeam -> new SizedTeam(mapTeam.getName(), mapTeam.getColor(), maxPerTeam.get()))
                .collect(Collectors.toList());

        joinRemainingAssignations(assignations, teams, maxPerTeam.get());

        return new HashSet<>(teams);
    }

    /**
     * Obtain every assignation pending and the match teams,
     * ordering them in equitable teams depending the {@link Match}
     * and {@link SubGameMode} capacity.
     * @param assignations to be distributed between teams
     * @param teams where assignations will be placed
     * @param maxMembers allowed for every team.
     */
    private void joinRemainingAssignations(Set<MatchAssignable> assignations, List<SizedTeam> teams, int maxMembers) {
        for (MatchAssignable assignable : assignations) {

            Set<MatchDoc.TeamMember> members = getProcessedMembers(assignable);
            int remaining = members.size();

            for (SizedTeam team : teams) {

                int playersThatCanJoin = maxMembers - team.getMembers().size();


                Comparator<MatchDoc.Team> teamComparator = Comparator.comparingInt(value -> value.getMembers().size());
                teams.sort(teamComparator);

                if (playersThatCanJoin >= remaining) {
                    members.forEach(team::addMember);
                    remaining = 0;
                } else {

                    remaining -= playersThatCanJoin;

                    Iterator<MatchDoc.TeamMember> memberIterator = members.iterator();

                    int used = 0;
                    while (memberIterator.hasNext() && used < playersThatCanJoin){
                        MatchDoc.TeamMember member = memberIterator.next();

                        team.addMember(member);
                        memberIterator.remove();

                        used++;
                    }
                }

                if (remaining == 0) {
                    break;
                }

            }
        }
    }

    /**
     * Get a {@link MatchAssignable} and group a set of
     * them, stamping it with a creation date and mapping
     * them to a {@link MatchDoc.TeamMember}
     * @param assignable to be mapped
     * @return set containing mapped assignation
     */
    private static Set<MatchDoc.TeamMember> getProcessedMembers(MatchAssignable assignable) {

        Set<String> generalAssignation = new HashSet<>(assignable.getInvolved());
        generalAssignation.add(assignable.getResponsible());

        Date joinedAt = new Date();
        return generalAssignation.stream().map(user -> new MatchDoc.TeamMember() {
            @Override
            public String getUser() {
                return user;
            }

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public Date getJoinedAt() {
                return joinedAt;
            }
        }).collect(Collectors.toSet());
    }

    /**
     * Find the actual match {@link GameMode}
     * id and {@link SubGameMode} id in order to know which is
     * the capacity of every team of the game.
     * @param match to be queried
     * @return optional indicating max capacity of each team.
     */
    private Optional<Integer> getMaxMembers(Match match) {
        try {
            GameMode mode = findService.findSync(match.getGameMode());

            if (mode.getSubTypes() == null) {
                throw new GameControlException("The requested mode does not have maximum members");
            }

            Optional<SubGameMode> subMode = mode
                    .getSubTypes().stream()
                    .filter(m -> m.getId().equalsIgnoreCase(match.getSubMode()))
                    .findAny();

            return subMode.map(SubGameMode::getTeamSize);

        } catch (Exception ignore) {}
        return Optional.empty();
    }

}
