package net.astrocube.commons.bukkit.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.admin.punishment.PunishmentChooserMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class PunishCommand implements CommandClass {

	private @Inject PunishmentChooserMenu punishmentChooserMenu;
	private @Inject MessageHandler messageHandler;
	private @Inject ObjectMapper objectMapper;
	private @Inject QueryService<User> queryService;
	private @Inject FindService<User> findService;

	@Command(names = "punish", permission = "commons.staff.punish.menu")
	public boolean punish(@Sender Player player, String punished) {

		ObjectNode nodes = objectMapper.createObjectNode();
		nodes.put("username", punished);

		findService.find(player.getDatabaseId()).callback(userResponse -> {

			if (!userResponse.isSuccessful() || !userResponse.getResponse().isPresent()) {
				messageHandler.sendIn(player, AlertModes.ERROR, "punish-menu.error");
				return;
			}

			queryService.query(nodes).callback(response -> {

				if (!response.isSuccessful() || !response.getResponse().isPresent()) {
					messageHandler.sendIn(player, AlertModes.ERROR, "punish-menu.error");
					return;
				}

				Optional<User> online = response.getResponse().get().getFoundModels().stream().findFirst();

				if (!online.isPresent()) {
					messageHandler.sendIn(player, AlertModes.ERROR, "commands.player.offline");
					return;
				}

				if (!online.get().getSession().isOnline()) {
					messageHandler.sendIn(player, AlertModes.ERROR, "commands.player.offline");
					return;
				}

				if (
					Group.getLowestPriority(online.get().getGroups()) <=
						Group.getLowestPriority(userResponse.getResponse().get().getGroups())) {
					messageHandler.sendIn(player, AlertModes.ERROR, "punish.lower");
					return;
				}

				Inventory inventory = punishmentChooserMenu.createPunishmentChooserMenu(
					player, userResponse.getResponse().get(), online.get());

				if (inventory != null) {
					player.openInventory(inventory);
				}

			});

		});

		return true;
	}
}