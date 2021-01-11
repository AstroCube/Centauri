package net.astrocube.api.bukkit.nametag.types;

import net.astrocube.api.bukkit.nametag.Nametag;
import org.bukkit.entity.Player;

public abstract class AbstractNametag implements Nametag {

    protected final Player recipient;
    protected final String tag;
    protected final String teamName;

    public AbstractNametag(Player recipient, String tag, String teamName) {
        this.recipient = recipient;
        this.tag = tag;
        this.teamName = teamName;
    }

    @Override
    public Player getRecipient() {
        return this.recipient;
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public String getTeamName() {
        return this.teamName;
    }

    protected static abstract class Builder<T extends Nametag, U extends Builder<T, U>> {

        protected final Player recipient;
        protected int priority = 0;

        public Builder(Player recipient) {
            this.recipient = recipient;
        }

        public abstract T build();

        public U priority(int priority) {
            this.priority = priority;
            return (U) this;
        }
    }
}