package net.astrocube.commons.bukkit.menu;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.core.punishment.CorePunishmentBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

public class MainStaffSanctionsMenu {

    // TODO: 3/12/2020 Re-work this class, implement the others menus and add compatibility to n-message.

    public Inventory mainSanctionsMenu(String punished) {

        return GUIBuilder.builder("Sanctions Menu", 3)
                .addItem(ItemClickable.builder(11)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName("Ban")
                                .setLore("Ban " + punished, "Click to select the reason")
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.BAN);
                            //Open the second inventory phase (Reason) and after (Duration)

                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName("Kick " + punished)
                                .setLore("Kick {player}", "Click to select the reason")
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.KICK);

                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(15)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName("Warn " + punished)
                                .setLore("Click to select the reason")
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.WARN);

                            return true;
                        })
                        .build()
                )
                .build();
    }
}