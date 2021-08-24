package net.astrocube.commons.bukkit.whisper;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface WhisperManager {

	CompletableFuture<WhisperResponse> sendWhisper(Player sender, User target, User senderIdentity, String message);

}
