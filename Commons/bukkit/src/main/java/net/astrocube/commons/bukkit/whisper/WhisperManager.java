package net.astrocube.commons.bukkit.whisper;

import com.google.common.util.concurrent.ListenableFuture;
import me.fixeddev.minecraft.player.Player;
import net.astrocube.api.core.virtual.user.User;

public interface WhisperManager {
    ListenableFuture<WhisperResponse> sendWhisper(Player sender, User target, User senderIdentity, String message);
}
