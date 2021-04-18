package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.core.message.MessageDefaults;
import net.astrocube.api.core.virtual.user.User;

import java.util.Set;

@MessageDefaults.ChannelName("gc-victory-assign")
public interface VictoryAssignMessage extends MatchActionMessage {

    /**
     * @return {@link User} id to mark as winners.
     */
    Set<String> getWinners();

}
