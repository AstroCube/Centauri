package net.astrocube.api.core.message;

public class ChannelMeta<T extends Message> {

    private final Class<T> type;
    private final String name;

    public ChannelMeta(Class<T> type, String name) {
        this.name = name;
        this.type = type;
    }

    public Class<T> type() {
        return type;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}