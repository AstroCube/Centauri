package net.astrocube.api.bukkit.nametag.types.animated;

import net.astrocube.api.bukkit.nametag.types.AbstractNametag;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class AnimatedNametag extends AbstractNametag {

    private final Plugin plugin;
    private final List<String> animation;

    public AnimatedNametag(Player recipient, String tag, String teamName, Plugin plugin, List<String> animation) {
        super(recipient, tag, teamName);
        this.plugin = plugin;
        this.animation = animation;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public List<String> getAnimation() {
        return animation;
    }

    public static Builder builder(Player recipient, Plugin plugin) {
        return new Builder(recipient, plugin);
    }

    public static class Builder extends AbstractNametag.Builder<AnimatedNametag, Builder> {

        private final Plugin plugin;

        private List<String> animation = new ArrayList<>();

        private Builder(Player recipient, Plugin plugin) {
            super(recipient);
            this.plugin = plugin;
        }

        @Override
        public AnimatedNametag build() {
            String playerName = this.recipient.getName();

            return new AnimatedNametag(
                    this.recipient,
                    playerName,
                    StringUtils.substring(this.priority + playerName, 0, 15),
                    this.plugin,
                    this.animation
            );
        }

        public Builder append(String line) {
            this.animation.add(line);
            return this;
        }

        public Builder setAnimation(List<String> animation) {
            this.animation = animation;
            return this;
        }
    }
}