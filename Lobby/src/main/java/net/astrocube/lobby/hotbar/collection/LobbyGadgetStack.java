package net.astrocube.lobby.hotbar.collection;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyGadgetStack {

    public static ItemStack get(MessageHandler provider, Player player) {

        ItemStack lobbySelectorBase = NBTUtils.addString(
                new ItemStack(Material.NETHER_STAR, 1),
                "actionable",
                "lobby_selector"
        );

        ItemMeta lobbyMeta = lobbySelectorBase.getItemMeta();
        lobbyMeta.setDisplayName(
                provider.get(player, "lobby.lobby-selector.title")
        );
        lobbyMeta.setLore(provider.getMany(player, "lobby.lobby-selector.gadget-lore"));
        lobbySelectorBase.setItemMeta(lobbyMeta);
        return lobbySelectorBase;

    }

}
