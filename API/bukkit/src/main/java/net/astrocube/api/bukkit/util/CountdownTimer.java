package net.astrocube.api.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CountdownTimer implements Runnable {

    private Plugin plugin;
    private Integer assignedTaskId;

    private int seconds;
    private int secondsLeft;
    // prevents beforeTimer from be executed more than once
    private boolean beforeTimerExecuted = false;

    private final Consumer<CountdownTimer> everySecond;
    @Nullable private final Runnable beforeTimer;
    @Nullable private final Runnable afterTimer;

    public CountdownTimer(
            Plugin plugin,
            int seconds,
            @Nullable Runnable beforeTimer,
            Consumer<CountdownTimer> everySecond,
            @Nullable Runnable afterTimer
    ) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.secondsLeft = seconds;
        this.beforeTimer = beforeTimer;
        this.afterTimer = afterTimer;
        this.everySecond = everySecond;
    }

    public CountdownTimer(
            Plugin plugin,
            int seconds,
            Consumer<CountdownTimer> everySecond,
            @Nullable Runnable afterTimer
    ) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.secondsLeft = seconds;
        this.beforeTimer = null;
        this.afterTimer = afterTimer;
        this.everySecond = everySecond;
    }

    public CountdownTimer(
            Plugin plugin,
            int seconds,
            @Nullable Runnable beforeTimer,
            Consumer<CountdownTimer> everySecond
    ) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.secondsLeft = seconds;
        this.beforeTimer = beforeTimer;
        this.afterTimer = null;
        this.everySecond = everySecond;
    }

    public CountdownTimer(
            Plugin plugin,
            int seconds,
            Consumer<CountdownTimer> everySecond
    ) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.secondsLeft = seconds;
        this.beforeTimer = null;
        this.afterTimer = null;
        this.everySecond = everySecond;
    }

    @Override
    public void run() {
        if (secondsLeft < 1) {
            if (afterTimer != null) afterTimer.run();
            if (assignedTaskId != null) Bukkit.getScheduler().cancelTask(assignedTaskId);
            return;
        }

        if (!beforeTimerExecuted && secondsLeft == seconds && beforeTimer != null) {
            beforeTimer.run();
            beforeTimerExecuted = true;
        }

        everySecond.accept(this);

        secondsLeft--;
    }

    public int getTotalSeconds() {
        return seconds;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public Integer getAssignedTaskId() {
        return assignedTaskId;
    }

    public void cancelCountdown() {
        Bukkit.getScheduler().cancelTask(assignedTaskId);
    }

    public void scheduleTimer() {
        this.assignedTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
    }

    public boolean isImportantSecond() {
        return ((this.secondsLeft % 15) == 0) || (this.secondsLeft < 6 && this.secondsLeft >= 1);
    }

}