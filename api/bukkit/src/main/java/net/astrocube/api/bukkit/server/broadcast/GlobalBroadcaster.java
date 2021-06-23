package net.astrocube.api.bukkit.server.broadcast;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GlobalBroadcaster {

	void broadcastMessage(String message) throws JsonProcessingException;

	void broadcastMessageInServer(String message);

}
