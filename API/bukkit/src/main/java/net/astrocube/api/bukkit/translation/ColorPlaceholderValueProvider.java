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
	private final Map<String, String> underscoredColors = new HashMap<>();

	public ColorPlaceholderValueProvider(ConfigurationHandle config) {
		super(config);
		colors.put("n", "\n");
		for (ChatColor color : ChatColor.values()) {
			String name = color.name().toLowerCase();
			if (name.indexOf('_') != -1) {
				underscoredColors.put(name, color.toString());
			} else {
				colors.put(name, color.toString());
			}
		}
	}

	@Override
	public @Nullable String getValue(TrackingContext context, String identifier, String parameters) {
		String value = underscoredColors.get(identifier + '_' + parameters);
		if (value != null) {
			return value;
		}
		return super.getValue(context, identifier, parameters);
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