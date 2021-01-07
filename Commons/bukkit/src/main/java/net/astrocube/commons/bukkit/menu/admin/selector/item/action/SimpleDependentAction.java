package net.astrocube.commons.bukkit.menu.admin.selector.item.action;

import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.commons.bukkit.menu.admin.selector.AdminSubGameModeSelectorMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleDependentAction implements DependentAction {

    @Inject
    private AdminSubGameModeSelectorMenu adminSubGameModeSelectorMenu;

    @Override
    public boolean constructClickActions(InventoryClickEvent inventoryClickEvent, GameMode gameMode) {

        Player player = (Player) inventoryClickEvent.getWhoClicked();

        switch (inventoryClickEvent.getClick()) {

            case LEFT:

                player.openInventory(adminSubGameModeSelectorMenu.createSubGameModeSelectorMenu(player));
                return true;

            case RIGHT:

                List<SubGameMode> gameModes = new ArrayList<>(gameMode.getSubTypes());

                int i = ThreadLocalRandom.current().nextInt(gameModes.size());

                SubGameMode subGameMode = gameModes.get(i); // TODO: 6/1/2021 Send to this sub-gamemode, but first I need the cloud
                return true;

            default:
                return true;
        }
    }
}