package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.core.message.MessageDefaults;
import net.astrocube.api.core.virtual.user.User;

@MessageDefaults.ChannelName("gc-disqualify")
public interface MatchDisqualifyMessage extends MatchActionMessage {

    /**
     * @return {@link User} id to assign as spectator.
     */
    String getUser();

}
