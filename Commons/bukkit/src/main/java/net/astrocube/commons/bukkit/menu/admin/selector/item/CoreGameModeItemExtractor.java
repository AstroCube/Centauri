package net.astrocube.commons.bukkit.menu.admin.selector.item;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.commons.bukkit.menu.admin.selector.item.action.DependentAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import team.unnamed.gui.abstraction.item.ItemClickable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CoreGameModeItemExtractor implements GameModeItemExtractor {

    @Inject
    private MessageHandler<Player> messageHandler;
    @Inject
    private DependentAction dependentAction;

    @Override
    public ItemClickable generateGameMode(GameMode gameMode, Player player) {

        ItemStack icon = new ItemStack(Material.DIRT);

        Material exchangeableMaterial = Material.getMaterial(gameMode.getNavigator().toUpperCase());
        if (exchangeableMaterial != null) {
            icon = new ItemStack(exchangeableMaterial);
        }

        ItemMeta iconMeta = icon.getItemMeta();
        List<String> baseLore = new ArrayList<>(messageHandler.getMany(player, "lobby.gameSelector.games." + gameMode.getId() + ".lore").getContents());

        baseLore.add(" ");
        baseLore.add(messageHandler.get(player, "lobby.gameSelector.gadget-playing"));

        iconMeta.setDisplayName(
                messageHandler.get(player, "lobby.gameSelector.games." + gameMode.getId() + ".title")
        );
        iconMeta.setLore(
                baseLore
        );
        icon.setItemMeta(iconMeta);

        return ItemClickable
                .builder(gameMode.getSlot())
                .setItemStack(icon)
                .setAction(inventoryClickEvent -> dependentAction.constructClickActions(inventoryClickEvent, gameMode))
                .build();
    }
}