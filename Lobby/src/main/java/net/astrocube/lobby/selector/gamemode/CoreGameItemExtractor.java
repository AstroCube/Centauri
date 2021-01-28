package net.astrocube.lobby.selector.gamemode;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameItemExtractor;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameSelectorRedirect;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import team.unnamed.gui.abstraction.item.ItemClickable;

import java.util.ArrayList;
import java.util.List;

public class CoreGameItemExtractor implements GameItemExtractor {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject GameSelectorRedirect gameSelectorRedirect;

    @Override
    public ItemClickable generate(GameMode gameModeDoc, Player player) {
        ItemStack icon = new ItemStack(Material.DIRT);

        Material exchangeableMaterial = Material.getMaterial(gameModeDoc.getNavigator().toUpperCase());
        if (exchangeableMaterial != null) icon = new ItemStack(exchangeableMaterial);

        ItemMeta iconMeta = icon.getItemMeta();
        List<String> baseLore = new ArrayList<>(messageHandler.getMany(player, "lobby.gameSelector.games." + gameModeDoc.getId() + ".lore").getContents());

        baseLore.add(" ");
        baseLore.add(messageHandler.get(player, "lobby.gameSelector.gadget-playing"));

        iconMeta.setDisplayName(
                messageHandler.get(player, "lobby.gameSelector.games." + gameModeDoc.getId() + ".title")
        );
        iconMeta.setLore(
                baseLore
        );
        icon.setItemMeta(iconMeta);

        //TODO: Link with cloud system with online number
        return ItemClickable.builder(gameModeDoc.getSlot())
                .setItemStack(icon)
                .setAction((block) -> {
                    gameSelectorRedirect.redirectPlayer(gameModeDoc, player);
                    return true;
                }).build();
    }
}