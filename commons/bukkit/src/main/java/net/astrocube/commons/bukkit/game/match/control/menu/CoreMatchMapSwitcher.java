package net.astrocube.commons.bukkit.game.match.control.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.MatchMapUpdater;
import net.astrocube.api.bukkit.game.match.control.menu.MatchLobbyMenuProvider;
import net.astrocube.api.bukkit.game.match.control.menu.MatchMapSwitcher;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.query.QueryService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Singleton
public class CoreMatchMapSwitcher implements MatchMapSwitcher {

	private @Inject ShapedMenuGenerator shapedMenuGenerator;
	private @Inject MessageHandler messageHandler;
	private @Inject MatchLobbyMenuProvider matchLobbyMenuProvider;
	private @Inject MatchMapUpdater matchMapUpdater;
	private @Inject GenericHeadHelper genericHeadHelper;
	private @Inject Plugin plugin;

	private @Inject QueryService<GameMap> queryService;
	private @Inject ObjectMapper mapper;

	@Override
	public void openMapMenu(Player player, Match match) throws Exception {

		player.openInventory(
			shapedMenuGenerator.generate(
				player,
				messageHandler.get(player, "game.admin.lobby.map.title"),
				(p) -> {
					try {
						matchLobbyMenuProvider.create(p);
					} catch (Exception e) {
						messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.error");
						plugin.getLogger().log(Level.SEVERE, "Error while generating player menu", e);
					}
				},
				generateMaps(player, match)
			)
		);

	}

	private Set<ShapedMenuGenerator.BaseClickable> generateMaps(Player player, Match match) throws Exception {

		ObjectNode nodes = mapper.createObjectNode();
		nodes.put("gamemode", match.getGameMode());
		nodes.put("subGamemode", match.getSubMode());

		return queryService.querySync(nodes).getFoundModels()
			.stream()
			.map(map -> new ShapedMenuGenerator.BaseClickable() {
				@Override
				public Consumer<Player> getClick() {
					return (p) -> {
						if (!(match.getMap() != null && match.getMap().equalsIgnoreCase(map.getId()))) {
							matchMapUpdater.updateMatch(match.getId(), map.getId(), player.getDatabaseIdentifier());
						} else {
							messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.map.error");
						}
						Bukkit.getScheduler().runTask(plugin, p::closeInventory);
					};
				}

				@Override
				public ItemStack getStack() {
					return genericHeadHelper.generateMetaAndPlace(
						new ItemStack(Material.PAPER),
						messageHandler.replacing(
							player, "game.admin.lobby.map.select.title",
							"%map%", map.getName()
						),
						messageHandler.getMany(
							player,
							!(match.getMap() != null && match.getMap().equalsIgnoreCase(map.getId())) ?
								"game.admin.lobby.map.select.lore" : "game.admin.lobby.map.select.lore-selected"
						)
					);
				}
			})
			.collect(Collectors.toSet());

	}

}
