package net.astrocube.commons.bukkit.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.message.Metadata;
import net.astrocube.api.core.player.ProxyKickRequest;
import net.astrocube.api.core.session.SessionPingMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.logging.Level;

public class SessionPingHandler implements MessageHandler<SessionPingMessage> {

	private @Inject Plugin plugin;
	private final Channel<SessionPingMessage> pingMessageChannel;
	private final Channel<ProxyKickRequest> proxyKickRequestChannel;

	@Inject
	SessionPingHandler(Messenger messenger) {
		pingMessageChannel = messenger.getChannel(SessionPingMessage.class);
		pingMessageChannel.addHandler(this);
		proxyKickRequestChannel = messenger.getChannel(ProxyKickRequest.class);
	}

	@Override
	public Class<SessionPingMessage> type() {
		return SessionPingMessage.class;
	}

	@Override
	public void handleDelivery(SessionPingMessage message, Metadata properties) {


		Player player = Bukkit.getPlayerByIdentifier(message.getUser());

		if (player != null) {

			if (message.getAction() == SessionPingMessage.Action.REQUEST) {

				try {
					pingMessageChannel.sendMessage(new SessionPingMessage() {
						@Override
						public String getUser() {
							return player.getDatabaseIdentifier();
						}

						@Override
						public Action getAction() {
							return Action.RESPONSE;
						}
					}, new HashMap<>());
				} catch (JsonProcessingException e) {
					plugin.getLogger().log(Level.SEVERE, "Error while sending session ping response", e);
				}

			}

			if (message.getAction() == SessionPingMessage.Action.DISCONNECT) {
				try {
					proxyKickRequestChannel.sendMessage(new ProxyKickRequest() {
						@Override
						public String getName() {
							return player.getName();
						}

						@Override
						public String getReason() {
							return ChatColor.RED + "Session invalidated due to lack of pingback";
						}
					}, new HashMap<>());
				} catch (JsonProcessingException e) {
					plugin.getLogger().log(Level.SEVERE, "Error while sending session ping kick request", e);
				}
			}

		}

	}

}
