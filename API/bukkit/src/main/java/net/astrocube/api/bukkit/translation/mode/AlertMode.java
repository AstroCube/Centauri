package net.astrocube.api.bukkit.translation.mode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.yushust.message.mode.Mode;
import org.bukkit.Sound;

@Getter
@AllArgsConstructor
public enum AlertMode implements Mode {

    INFO(false, Sound.NOTE_PLING),
    ERROR(false, Sound.NOTE_BASS),
    MUTED(false, Sound.NOTE_SNARE_DRUM),
    VOID(true, null);

    private final boolean def;
    private final Sound sound;

    @Override
    public boolean isDefault() {
        return def;
    }

}
