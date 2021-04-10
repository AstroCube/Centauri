package net.astrocube.commons.bukkit.whisper;

import me.fixeddev.minecraft.player.Player;
import net.astrocube.api.core.virtual.user.User;

import java.util.concurrent.CompletableFuture;

public interface WhisperManager {
    CompletableFuture<WhisperResponse> sendWhisper(Player sender, User target, User senderIdentity, String message);
}
