package net.astrocube.api.bukkit.translation;

import me.yushust.message.config.ConfigurationHandle;
import me.yushust.message.format.PlaceholderValueProviderImpl;
import me.yushust.message.track.TrackingContext;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ColorPlaceholderValueProvider extends PlaceholderValueProviderImpl {

    private final Map<String, String> colors = new HashMap<>();

    public ColorPlaceholderValueProvider(ConfigurationHandle config) {
        super(config);
        colors.put("n", "\n");
        for (ChatColor color : ChatColor.values()) {
            colors.put("%" + color.name().toLowerCase() + "%", color.toString());
        }
    }

    @Override
    public @Nullable String getValue(TrackingContext context, String identifier) {
        String value = colors.get(identifier);
        if (value != null) {
            return value;
        }
        return super.getValue(context, identifier);
    }
}
