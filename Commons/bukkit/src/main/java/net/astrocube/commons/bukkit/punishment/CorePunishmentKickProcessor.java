package net.astrocube.commons.bukkit.punishment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.PunishmentKickProcessor;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.player.ProxyKickRequest;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.PrettyTimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class CorePunishmentKickProcessor implements PunishmentKickProcessor {

	private final Channel<ProxyKickRequest> channel;
	private @Inject MessageHandler messageHandler;
	private @Inject QueryService<Punishment> queryService;
	private @Inject ObjectMapper mapper;
	private @Inject Plugin plugin;

	@Inject
	public CorePunishmentKickProcessor(Messenger jedisMessenger) {
		channel = jedisMessenger.getChannel(ProxyKickRequest.class);
	}

	@Override
	public void processKick(Punishment punishment, Player player, User user) throws JsonProcessingException {

		String translation = "punish.kick.message";

		if (punishment.getType() == PunishmentDoc.Identity.Type.BAN) {
			translation = "punish.ban.message-permanent";
		}

		if (punishment.getExpiration() != null) {
			translation = "punish.ban.message-temporal";
		}

		String finalMessage = messageHandler.replacing(
			player, translation,
			"%reason%", punishment.getReason(),
			"%expires%", punishment.getExpiration() == null ? "" : PrettyTimeUtils.getHumanDate(
				Date.from(punishment.getExpiration().atZone(ZoneOffset.systemDefault()).toInstant()),
				user.getLanguage()
			)
		);

		if (plugin.getConfig().getBoolean("server.sandbox")) {
			Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(finalMessage));
		} else {
			channel.sendMessage(new ProxyKickRequest() {
				@Override
				public String getName() {
					return player.getName();
				}

				@Override
				public String getReason() {
					return finalMessage;
				}
			}, new HashMap<>());
		}

	}

	@Override
	public void validateKick(Player player, User user) throws Exception {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", "Ban");
		node.put("punished", user.getId());
		node.put("active", true);

		Optional<Punishment> activePunishment = queryService.querySync(node).getFoundModels().stream().filter(
			punishment -> punishment.getExpiration() == null ||
				punishment.getExpiration().isAfter(LocalDateTime.now())

		).collect(Collectors.toSet()).stream().findFirst();

		if (activePunishment.isPresent()) {
			processKick(activePunishment.get(), player, user);
		}
	}

}
