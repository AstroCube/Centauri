package net.astrocube.lobby.selector.gamemode;

import com.google.inject.Inject;
import me.yushust.message.core.MessageProvider;
import me.yushust.message.core.handle.StringList;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameItemExtractor;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.api.item.ItemClickable;
import team.unnamed.gui.item.DefaultItemClickable;

import java.util.logging.Level;

public class CoreGameItemExtractor implements GameItemExtractor {

    private @Inject Plugin plugin;
    private @Inject MessageProvider<Player> messageProvider;

    @Override
    public ItemClickable generate(GameMode gameMode, Player player) {
        ItemStack icon = new ItemStack(Material.DIRT);

        try {
            icon = new ItemStack(Material.getMaterial(gameMode.getName()));
        } catch (IllegalArgumentException exception) {
            plugin.getLogger().log(Level.WARNING, "No parsable item at game menu", exception);
        }

        ItemMeta iconMeta = icon.getItemMeta();
        StringList baseLore = messageProvider.getMessages(player, "lobby.gameSelector.games." + gameMode.getId() + ".lore");

        baseLore.add(" ");
        baseLore.add(messageProvider.getMessage(player, "lobby.gameSelector.gadget-playing"));

        iconMeta.setDisplayName(
                messageProvider.getMessage(player, "lobby.gameSelector.games." + gameMode.getId() + ".title")
        );
        iconMeta.setLore(
                messageProvider.getMessages(player, "lobby.gameSelector.games." + gameMode.getId() + ".lore")
        );
        icon.setItemMeta(iconMeta);

        //TODO: Link with cloud system
        return new DefaultItemClickable(gameMode.getSlot(), icon, (block) -> true);
    }
}
