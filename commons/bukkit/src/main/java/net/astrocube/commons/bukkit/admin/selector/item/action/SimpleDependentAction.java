package net.astrocube.commons.bukkit.admin.selector.item.action;

import net.astrocube.api.bukkit.game.spectator.SpectateRequestAssigner;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.commons.bukkit.admin.selector.AdminSubGameModeSelectorMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleDependentAction implements DependentAction {

	@Inject private AdminSubGameModeSelectorMenu adminSubGameModeSelectorMenu;
	private @Inject SpectateRequestAssigner spectateRequestAssigner;

	@Override
	public boolean constructClickActions(InventoryClickEvent inventoryClickEvent, GameMode gameMode) {

		Player player = (Player) inventoryClickEvent.getWhoClicked();

		switch (inventoryClickEvent.getClick()) {

			case LEFT:

				player.openInventory(adminSubGameModeSelectorMenu.createSubGameModeSelectorMenu(player, gameMode));
				return true;

			case RIGHT:

				List<SubGameMode> gameModes = new ArrayList<>(gameMode.getSubTypes());

				if (gameModes.isEmpty()) {
					return false;
				}

				int i = ThreadLocalRandom.current().nextInt(gameModes.size());

				SubGameMode subGameMode = gameModes.get(i);

				spectateRequestAssigner.assignRequest(gameMode.getId(), subGameMode.getId(), player.getDatabaseId());

				return true;

			default:
				return true;
		}
	}
}