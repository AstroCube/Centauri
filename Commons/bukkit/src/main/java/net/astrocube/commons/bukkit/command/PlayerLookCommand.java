package net.astrocube.commons.bukkit.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.lookup.PunishmentLookupMenu;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

public class PlayerLookCommand implements CommandClass {

	private @Inject FindService<User> userFindService;
	private @Inject MessageHandler messageHandler;
	private @Inject QueryService<Punishment> queryService;
	private @Inject QueryService<User> userQueryService;
	private @Inject PunishmentLookupMenu punishmentLookupMenu;
	private @Inject ObjectMapper objectMapper;

	@Command(names = {"look", "lookup"})
	public boolean lookupCommand(@Sender Player player, OfflinePlayer offlineTarget) {

		ObjectNode nodes = objectMapper.createObjectNode();
		nodes.put("username", offlineTarget.getName());

		userFindService.find(player.getDatabaseIdentifier()).callback(userResponse -> {

			if (!userResponse.isSuccessful() || !userResponse.getResponse().isPresent()) {
				messageHandler.sendIn(player, AlertModes.ERROR, "punish-menu.error");
				return;
			}

			userQueryService.query(nodes).callback(response -> {

				if (!response.isSuccessful() || !response.getResponse().isPresent()) {
					messageHandler.sendIn(player, AlertModes.ERROR, "punish-menu.error");
					return;
				}

				Optional<User> online = response.getResponse().get().getFoundModels().stream().findFirst();

				if (!online.isPresent()) {
					messageHandler.sendIn(player, AlertModes.ERROR, "commands.player.offline");
					return;
				}

				ObjectNode objectNode = objectMapper.createObjectNode()
					.put("punished", online.get().getId());


				queryService.query(objectNode).callback(punishmentResponse -> {

					if (!punishmentResponse.isSuccessful() || !punishmentResponse.getResponse().isPresent()) {
						messageHandler.sendIn(player, AlertModes.ERROR, "punish-menu.error");
						return;
					}

					Set<Punishment> punishments = punishmentResponse.getResponse().get().getFoundModels();

					if (punishments.isEmpty()) {
						messageHandler.send(player, "punishment-expiration-menu.empty");
						return;
					}

					punishmentLookupMenu.generateMenu(punishments, userResponse.getResponse().get(), online.get(), player, 1);

				});

			});

		});

		return true;
	}

}