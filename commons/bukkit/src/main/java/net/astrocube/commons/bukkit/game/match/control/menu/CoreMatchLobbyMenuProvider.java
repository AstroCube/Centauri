package net.astrocube.commons.bukkit.game.match.control.menu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchAvailabilityChecker;
import net.astrocube.api.bukkit.game.match.control.menu.MatchLobbyMenuProvider;
import net.astrocube.api.bukkit.game.match.control.menu.MatchMapSwitcher;
import net.astrocube.api.bukkit.game.match.control.menu.MatchPrivatizeSwitcher;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreMatchLobbyMenuProvider implements MatchLobbyMenuProvider {

	private @Inject MessageHandler messageHandler;
	private @Inject GenericHeadHelper genericHeadHelper;
	private @Inject ActualMatchCache actualMatchCache;
	private @Inject Plugin plugin;

	private @Inject MatchPrivatizeSwitcher matchPrivatizeSwitcher;
	private @Inject MatchAvailabilityChecker matchAvailabilityChecker;
	private @Inject MatchMapSwitcher matchMapSwitcher;
	private @Inject FindService<GameMap> findService;

	@Override
	public void create(Player player) throws Exception {


		if (!plugin.getConfig().getBoolean("server.sandbox")) {
			matchAvailabilityChecker.clearLegitMatches(player.getDatabaseIdentifier());
		}

		Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

		if (!matchOptional.isPresent()) {
			messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.not-active");
			return;
		}

		Match match = matchOptional.get();

		GUIBuilder builder = GUIBuilder.builder(
			messageHandler.get(player, "game.admin.lobby.title"),
			3
		);

		String actualMap = messageHandler.get(player, "game.admin.lobby.map.none");

		if (match.getMap() != null) {
			actualMap = findService.findSync(match.getMap()).getName();
		}


		builder.addItem(generateMapSelectorButton(player, match, actualMap));

		if (match.isPrivate()) {
			builder.addItem(generateDeactivateButton(player));
		} else {
			builder.addItem(generateActivateButton(player));
		}

		builder.addItem(generateSummonButton(player));

		player.openInventory(builder.build());

	}

	private ItemClickable generateActivateButton(Player player) {
		return genericHeadHelper.generateItem(
			genericHeadHelper.generateMetaAndPlace(
				new ItemStack(Material.REDSTONE_BLOCK),
				messageHandler.get(player, "game.admin.lobby.icons.private.enable.title"),
				messageHandler.getMany(player, "game.admin.lobby.icons.private.enable.lore")
			),
			13,
			ClickType.LEFT,
			() -> {
				try {
					matchPrivatizeSwitcher.switchPrivatization(player);
					create(player);
				} catch (Exception e) {
					plugin.getLogger().log(Level.SEVERE, "Error while updating privatization", e);
					messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.privatizing.error");
				}
			}
		);
	}

	private ItemClickable generateDeactivateButton(Player player) {
		return genericHeadHelper.generateItem(
			genericHeadHelper.generateMetaAndPlace(
				new ItemStack(Material.EMERALD_BLOCK),
				messageHandler.get(player, "game.admin.lobby.icons.private.disable.title"),
				messageHandler.getMany(player, "game.admin.lobby.icons.private.disable.lore")
			),
			13,
			ClickType.LEFT,
			() -> {
				try {
					matchPrivatizeSwitcher.switchPrivatization(player);
					create(player);
				} catch (Exception e) {
					plugin.getLogger().log(Level.SEVERE, "Error while updating privatization", e);
					messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.privatizing.error");
				}
			}
		);
	}

	private ItemClickable generateMapSelectorButton(Player player, Match match, String map) {
		return genericHeadHelper.generateItem(
			genericHeadHelper.generateMetaAndPlace(
				new ItemStack(Material.MAP),
				messageHandler.get(player, "game.admin.lobby.icons.map.title"),
				messageHandler.replacingMany(
					player, "game.admin.lobby.icons.map.lore",
					"%actual%", map
				)
			),
			11,
			ClickType.LEFT,
			() -> {
				try {
					matchMapSwitcher.openMapMenu(player, match);
				} catch (Exception e) {
					plugin.getLogger().log(Level.SEVERE, "Error while updating privatization", e);
					messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.map.error");
				}
			}
		);
	}

	private ItemClickable generateSummonButton(Player player) {
		return genericHeadHelper.generateItemCancellingEvent(
			genericHeadHelper.generateMetaAndPlace(
				new ItemStack(Material.EYE_OF_ENDER),
				messageHandler.get(player, "game.admin.lobby.icons.summon.title"),
				messageHandler.getMany(player, "game.admin.lobby.icons.summon.lore")
			),
			15,
			ClickType.LEFT
		);
	}

}
