package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.message.Message;

import java.util.Set;

public interface TeamAssignMessage extends Message {

    /**
     * @return {@link Match} teams to register at the match.
     */
    Set<MatchDoc.Team> getMatch();

}
