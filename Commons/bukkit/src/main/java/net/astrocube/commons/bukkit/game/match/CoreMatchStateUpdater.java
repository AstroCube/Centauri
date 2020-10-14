package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.update.UpdateService;

@Singleton
public class CoreMatchStateUpdater implements MatchStateUpdater {

    private @Inject FindService<Match> findService;
    private @Inject UpdateService<Match, MatchDoc.Partial> updateService;

    @Override
    public void updateMatch(Match match, MatchDoc.Status status) {

        findService.find(match.getId()).callback(matchResponse -> {

            if (matchResponse.isSuccessful() && )


        });

    }

}
