package net.astrocube.api.bukkit.game.countdown;

import net.astrocube.api.bukkit.virtual.game.match.Match;

public interface CountdownAlerter {

    void alertCountdownSecond(Match match, int second);

}
