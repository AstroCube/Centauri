package net.astrocube.commons.bukkit.nametag.animated;

import net.astrocube.api.bukkit.nametag.types.animated.AnimatedNametag;
import net.astrocube.api.bukkit.nametag.types.animated.RenderedAnimatedNametag;
import net.astrocube.api.bukkit.nametag.types.animated.TaskHandler;
import net.astrocube.commons.bukkit.nametag.SimpleRenderedNametag;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Set;

public class CoreRenderedAnimatedNametag extends SimpleRenderedNametag implements RenderedAnimatedNametag {

    private final List<String> animation;
    private final TaskHandler taskHandler;

    private int current = 0;

    public CoreRenderedAnimatedNametag(AnimatedNametag nametag, Set<Entity> spawnedEntities, Player viewer) {
        super(nametag, spawnedEntities, viewer);
        this.animation = nametag.getAnimation();

        this.taskHandler = new TaskHandler() {
            @Override
            public BukkitTask createTask() {
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        update();
                    }
                }.runTaskTimer(nametag.getPlugin(), 0L, 2L);
            }
        };
    }

    @Override
    public void hide() {
        super.hide();
        this.taskHandler.stop();
    }

    @Override
    public void show() {
        super.show();
        this.taskHandler.start();
    }

    @Override
    public void update() {
        if (animation.isEmpty()) return;
        if (current >= animation.size()) current = 0;

        this.setTag(this.animation.get(current));
        current++;
    }
}