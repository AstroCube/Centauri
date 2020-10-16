package net.astrocube.commons.bukkit.game.matchmaking;

import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class CoreIdealMatchSelector implements IdealMatchSelector {

    @Override
    public Optional<Match> sortAvailableMatches(Set<Match> matches) {
        return matches.stream().max(new Comparator<Match>() {
            @Override
            public int compare(Match o1, Match o2) {
                return 0;
            }
        });
    }

}
