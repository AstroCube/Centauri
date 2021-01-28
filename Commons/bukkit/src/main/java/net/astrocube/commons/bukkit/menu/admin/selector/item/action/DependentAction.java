package net.astrocube.commons.bukkit.menu.admin.selector.item.action;

import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface DependentAction {

    boolean constructClickActions(InventoryClickEvent inventoryClickEvent, GameMode gameMode);
}