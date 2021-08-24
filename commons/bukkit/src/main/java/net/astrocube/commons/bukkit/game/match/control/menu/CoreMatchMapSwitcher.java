package net.astrocube.commons.bukkit.game.match.control.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.game.match.MatchMapUpdater;
import net.astrocube.api.bukkit.game.match.control.menu.MatchLobbyMenuProvider;
import net.astrocube.api.bukkit.game.match.control.menu.MatchMapSwitcher;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.query.QueryService;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;

@Singleton
public class CoreMatchMapSwitcher implements MatchMapSwitcher {

	private @Inject ShapedMenuGenerator shapedMenuGenerator;
	private @Inject MessageHandler messageHandler;
	private @Inject MatchLobbyMenuProvider matchLobbyMenuProvider;
	private @Inject MatchMapUpdater matchMapUpdater;
	private @Inject Plugin plugin;

	private @Inject QueryService<GameMap> queryService;
	private @Inject ObjectMapper mapper;

	@Override
	public void openMapMenu(Player player, Match match) throws Exception {

		player.openInventory(
			shapedMenuGenerator.generate(
				player,
				messageHandler.get(player, "game.admin.lobby.map.title"),
				() -> {
					try {
						matchLobbyMenuProvider.create(player);
					} catch (Exception e) {
						messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.error");
						plugin.getLogger().log(Level.SEVERE, "Error while generating player menu", e);
					}
				},
				GameMap.class,
				getEntities(match),
				generateParser(player, match)
			)
		);

	}

	private Set<GameMap> getEntities(Match match) throws Exception {
		ObjectNode node = mapper.createObjectNode();
		node.put("gamemode", match.getGameMode());
		node.put("subGamemode", match.getSubMode());

		return queryService.querySync(node).getFoundModels();
	}

	private Function<GameMap, ItemClickable> generateParser(Player player, Match match) {
		return gameMap -> {
			boolean selected = !(match.getMap() != null && match.getMap().equalsIgnoreCase(gameMap.getId()));

			return ItemClickable.builder()
				.setItemStack(ItemBuilder.newBuilder(Material.PAPER)
					.setName(messageHandler.replacing(
						player, "game.admin.lobby.map.select.title",
						"%map%", gameMap.getName()
					))
					.setLore(messageHandler.getMany(
						player,
						selected ? "game.admin.lobby.map.select.lore" : "game.admin.lobby.map.select.lore-selected"
					))
					.build()
				)
				.setAction(event -> {
					if (selected) {
						matchMapUpdater.updateMatch(match.getId(), gameMap.getId(), player.getDatabaseId());
					} else {
						messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.map.error");
					}

					Bukkit.getScheduler().runTask(plugin, (Runnable) player::closeInventory);

					return true;
				})
				.build();
		};
	}

}
