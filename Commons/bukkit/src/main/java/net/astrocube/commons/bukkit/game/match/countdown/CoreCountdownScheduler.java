package net.astrocube.commons.bukkit.game.match.countdown;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.countdown.CountdownAlerter;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.commons.bukkit.utils.CountdownTimer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreCountdownScheduler implements CountdownScheduler {

    private final Plugin plugin;
    private final Map<String, Integer> scheduledTimers;
    private final CountdownAlerter countdownAlerter;

    @Inject
    public CoreCountdownScheduler(Plugin plugin, CountdownAlerter countdownAlerter) {
        this.plugin = plugin;
        this.scheduledTimers = new HashMap<>();
        this.countdownAlerter = countdownAlerter;
    }

    @Override
    public void scheduleMatchCountdown(Match match) {
        scheduleMatchCountdown(match, 30, false);
    }

    @Override
    public void scheduleMatchCountdown(Match match, int seconds, boolean force) {

        if (force) {
            cancelMatchCountdown(match);
        }

        if (force || !scheduledTimers.containsKey(match.getId())) {
            CountdownTimer runnable = new CountdownTimer(
                    plugin,
                    1,
                    (sec) -> countdownAlerter.alertCountdownSecond(match, seconds),
                    () -> {}
            );
            runnable.scheduleTimer();
            this.scheduledTimers.put(match.getId(), runnable.getAssignedTaskId());
        }

    }

    @Override
    public void cancelMatchCountdown(Match match) {
        Bukkit.getScheduler().cancelTask(this.scheduledTimers.get(match.getId()));
        this.scheduledTimers.remove(match.getId());
    }

}
