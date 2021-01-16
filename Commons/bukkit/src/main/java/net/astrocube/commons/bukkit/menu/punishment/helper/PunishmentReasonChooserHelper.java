package net.astrocube.commons.bukkit.menu.punishment.helper;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentExpirationChooserMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PunishmentReasonChooserHelper {

    @Inject
    private Plugin plugin;
    @Inject
    private PunishmentExpirationChooserMenu punishmentExpirationChooserMenu;

    public List<ItemClickable> buildPunishReasons(Player player, PunishmentBuilder punishmentBuilder) {

        String type = punishmentBuilder.getType().toString().toLowerCase();

        List<ItemClickable> itemClickables = new ArrayList<>();

        for (String key : plugin.getConfig().getConfigurationSection(type + "-reasons").getKeys(false)) {

            ItemClickable itemClickable = ItemClickable.builder(plugin.getConfig().getInt(type + "-reasons." + key + ".slot"))
                    .setItemStack(ItemBuilder.newBuilder(Material.PAPER)

                            .setName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(type + "-reasons." + key + ".name")))
                            .setLore(colorize(plugin.getConfig().getStringList(type + "-reasons." + key + ".lore")))
                            .build())

                    .setAction(event -> {
                        punishmentBuilder.setReason(plugin.getConfig().getString(type + "-reasons." + key + ".punish-reason"));
                        player.openInventory(punishmentExpirationChooserMenu.createPunishmentExpirationChooserMenu(player, punishmentBuilder));
                        return true;
                    })
                    .build();

            itemClickables.add(itemClickable);
        }

        return itemClickables;
    }

    private List<String> colorize(List<String> list) {
        list.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
        return list;
    }
}