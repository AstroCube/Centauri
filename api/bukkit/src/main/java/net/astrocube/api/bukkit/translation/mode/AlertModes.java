package net.astrocube.api.bukkit.translation.mode;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Sound;

import java.util.Map;

public final class AlertModes {

	private AlertModes() {
	}

	public static final String INFO = "info";
	public static final String ERROR = "error";
	public static final String MUTED = "muted";
	public static final String VOID = "default";

	public static final Map<String, Sound> SOUNDS = ImmutableMap.<String, Sound>builder()
		.put(INFO, Sound.BLOCK_NOTE_BLOCK_PLING)
		.put(ERROR, Sound.BLOCK_NOTE_BLOCK_BASS)
		.put(MUTED, Sound.BLOCK_NOTE_BLOCK_SNARE)
		.build();

}
