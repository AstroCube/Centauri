package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.core.virtual.user.User;

public interface MatchDisqualifyMessage extends MatchActionMessage {

    /**
     * @return {@link User} id to assign as spectator.
     */
    String getUser();

}
