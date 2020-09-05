package net.astrocube.lobby.selector.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyIconExtractor;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySelectorWrapper;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyServerRedirect;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySwitchStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import team.unnamed.gui.api.item.ItemClickable;
import team.unnamed.gui.item.DefaultItemClickable;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CoreLobbyIconExtractor implements LobbyIconExtractor {

    private @Inject MessageProvider<Player> messageProvider;
    private @Inject LobbyServerRedirect lobbyServerRedirect;

    @Override
    public ItemClickable getLobbyIcon(LobbySelectorWrapper wrapper, Player player, int position) {

        ChatColor color = ChatColor.YELLOW;
        LobbySwitchStatus status = LobbySwitchStatus.SUCCESS;
        String translation = "connect";
        ItemStack itemStack = new ItemStack(Material.QUARTZ_BLOCK, wrapper.getNumber());

        if (wrapper.isFull()) {
            color = ChatColor.RED;
            translation = "full";
            status = LobbySwitchStatus.FULL;
            itemStack = new ItemStack(Material.STAINED_GLASS, wrapper.getNumber(), (short) 14);
        }

        if (wrapper.getName().equalsIgnoreCase(Bukkit.getServerName())) {
            color = ChatColor.GREEN;
            translation = "already";
            status = LobbySwitchStatus.CYCLIC;
            itemStack = new ItemStack(Material.STAINED_GLASS, wrapper.getNumber(), (short) 13);
        }

        ItemMeta meta = itemStack.getItemMeta();
        List<String> loreArray = new ArrayList<>();

        meta.setDisplayName(color + wrapper.getName());

        loreArray.add(
                messageProvider.getMessage(player, "lobby.lobby-selector.indicators.connected")
                .replace("%%users%%", wrapper.getConnected() + "")
                .replace("%%total%%", wrapper.getMax() + "")
        );
        loreArray.add(" ");
        loreArray.add(
                color + messageProvider.getMessage(player, "lobby.lobby-selector.indicators." + translation)
        );

        meta.setLore(loreArray);
        itemStack.setItemMeta(meta);

        LobbySwitchStatus finalStatus = status;
        return new DefaultItemClickable(position, itemStack, (event) -> {
            event.getWhoClicked().closeInventory();
            lobbyServerRedirect.redirectPlayer(player, wrapper, finalStatus);
            return true;
        });
    }

}
