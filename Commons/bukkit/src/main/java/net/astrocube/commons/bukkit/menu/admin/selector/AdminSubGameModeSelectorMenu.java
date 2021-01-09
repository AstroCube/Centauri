package net.astrocube.commons.bukkit.menu.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;

public class AdminSubGameModeSelectorMenu {

    @Inject
    private MessageHandler<Player> playerMessageHandler;

    public Inventory createSubGameModeSelectorMenu(Player player, GameMode gameMode) {

        GUIBuilder guiBuilder = GUIBuilder.builder(playerMessageHandler.get(player, ""), 3); // TODO: 6/1/2021 Start and finish this gui

        for (SubGameMode subGameMode : gameMode.getSubTypes()) {

            guiBuilder
                    .addItem(ItemClickable.builder(0)
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(playerMessageHandler.get(player, ""))
                                    .setLore(playerMessageHandler.getMany(player, ""))
                                    .build())
                            .setAction(InventoryClickEvent -> {
                                // TODO: 9/1/2021 Send to the requested sub-gamemode
                                return true;
                            })
                            .build());

        }

        return guiBuilder.build();
    }
}