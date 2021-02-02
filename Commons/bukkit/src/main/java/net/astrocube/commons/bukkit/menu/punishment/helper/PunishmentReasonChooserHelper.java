package net.astrocube.commons.bukkit.menu.punishment.helper;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentExpirationChooserMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PunishmentReasonChooserHelper {

    private @Inject Plugin plugin;
    private @Inject PunishmentExpirationChooserMenu punishmentExpirationChooserMenu;
    private @Inject MessageHandler messageHandler;

    public List<ItemClickable> buildPunishReasons(Player player, PunishmentBuilder punishmentBuilder) {

        String type = punishmentBuilder.getType().toString().toLowerCase();

        List<ItemClickable> clickableArray = new ArrayList<>();

        int index = 10;
        for (Object key : plugin.getConfig().getList("admin.punishments.reasons")) {

            Map<String, Object> linkedKey = (Map<String, Object>) key;

            if (linkedKey.get("type").toString().equalsIgnoreCase(type)) {

                while (MenuUtils.isMarkedSlot(index)) {
                    index++;
                }

                ItemClickable itemClickable = ItemClickable.builder(index)
                        .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                .setName(ChatColor.AQUA + messageHandler.get(player, "punish-menu.reasons." + linkedKey.get("name") + ".title"))
                                .setLore(messageHandler.get(player, "punish-menu.reasons." + linkedKey.get("lore") + "title"))
                                .build())

                        .setAction(event -> {
                            punishmentBuilder.setReason(messageHandler.get(player, "punish-menu.reasons." + linkedKey.get("lore") + ".reason"));
                            player.openInventory(punishmentExpirationChooserMenu.createPunishmentExpirationChooserMenu(player, punishmentBuilder));
                            return true;
                        })
                        .build();

                clickableArray.add(itemClickable);

            }
        }

        return clickableArray;
    }


}