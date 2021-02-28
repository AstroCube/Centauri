package net.astrocube.commons.bukkit.game.match.control.menu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.control.menu.MatchLobbyMenuProvider;
import net.astrocube.api.bukkit.game.match.control.menu.MatchMapSwitcher;
import net.astrocube.api.bukkit.game.match.control.menu.MatchPrivatizeSwitcher;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.commons.bukkit.menu.GenericHeadHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;

import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreMatchLobbyMenuProvider implements MatchLobbyMenuProvider {

    private @Inject MessageHandler messageHandler;
    private @Inject GenericHeadHelper genericHeadHelper;
    private @Inject ActualMatchCache actualMatchCache;
    private @Inject Plugin plugin;

    private @Inject MatchPrivatizeSwitcher matchPrivatizeSwitcher;
    private @Inject MatchMapSwitcher matchMapSwitcher;

    @Override
    public void create(Player player) throws Exception {

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

        builder.addItem(generateMapSelectorButton(player, match));

        if (match.isPrivate()) {
            builder.addItem(generateDeactivateButton(player));
        } else {
            builder.addItem(generateActivateButton(player));
        }

        builder.addItem(generateSummonButton(player));

        player.openInventory(builder.build());

    }

    private ItemClickable generateActivateButton(Player player) {
        return genericHeadHelper.generateDefaultClickable(
                genericHeadHelper.generateMetaAndPlace(
                        new ItemStack(Material.EMERALD),
                        messageHandler.get(player, "game.admin.lobby.icons.private.enable.title"),
                        messageHandler.getMany(player, "game.admin.lobby.icons.private.enable.lore")
                ),
                12,
                ClickType.LEFT,
                (p) -> {
                    try {
                        matchPrivatizeSwitcher.switchPrivatization(player);
                    } catch (Exception e) {
                        plugin.getLogger().log(Level.SEVERE, "Error while updating privatization", e);
                        messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.privatizing.error");
                    }
                }
        );
    }

    private ItemClickable generateDeactivateButton(Player player) {
        return genericHeadHelper.generateDefaultClickable(
                genericHeadHelper.generateMetaAndPlace(
                        new ItemStack(Material.REDSTONE_BLOCK),
                        messageHandler.get(player, "game.admin.lobby.icons.private.disable.title"),
                        messageHandler.getMany(player, "game.admin.lobby.icons.private.disable.lore")
                ),
                12,
                ClickType.LEFT,
                (p) -> {
                    try {
                        matchPrivatizeSwitcher.switchPrivatization(player);
                    } catch (Exception e) {
                        plugin.getLogger().log(Level.SEVERE, "Error while updating privatization", e);
                        messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.privatizing.error");
                    }
                }
        );
    }

    private ItemClickable generateMapSelectorButton(Player player, Match match) {
        return genericHeadHelper.generateDefaultClickable(
                genericHeadHelper.generateMetaAndPlace(
                        new ItemStack(Material.MAP),
                        messageHandler.get(player, "game.admin.lobby.icons.map.title"),
                        messageHandler.getMany(player, "game.admin.lobby.icons.map.lore")
                ),
                10,
                ClickType.LEFT,
                (p) -> {
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
        return genericHeadHelper.generateDefaultClickable(
                genericHeadHelper.generateMetaAndPlace(
                        new ItemStack(Material.EYE_OF_ENDER),
                        messageHandler.get(player, "game.admin.lobby.icons.map.title"),
                        messageHandler.getMany(player, "game.admin.lobby.icons.map.lore")
                ),
                14,
                ClickType.LEFT,
                (p) -> {

                }
        );
    }

}
