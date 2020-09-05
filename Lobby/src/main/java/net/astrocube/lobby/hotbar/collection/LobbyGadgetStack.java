package net.astrocube.lobby.hotbar.collection;

import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyGadgetStack {

    public static ItemStack get(MessageProvider<Player> provider, Player player) {

        ItemStack lobbySelectorBase = NBTUtils.addString(
                new ItemStack(Material.NETHER_STAR, 1),
                "actionable",
                "lobby_selector"
        );

        ItemMeta lobbyMeta = lobbySelectorBase.getItemMeta();
        lobbyMeta.setDisplayName(
                provider.getMessage(player, "lobby.lobby-selector.title")
        );
        lobbyMeta.setLore(provider.getMessages(player, "lobby.lobby-selector.gadget-lore"));
        lobbySelectorBase.setItemMeta(lobbyMeta);
        return lobbySelectorBase;

    }

}
