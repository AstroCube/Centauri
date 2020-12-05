package net.astrocube.commons.bukkit.menu;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.core.gui.type.GUIBuilder;

public class PunishmentReasonChooserMenu {

    public Inventory createPunishmentReasonChooserMenu(Player player,
                                                       MessageHandler<Player> messageHandler,
                                                       PunishmentBuilder punishmentBuilder) {

        // TODO: 4/12/2020 Implement the items, and ask if the punishments can have different reasons, mean if depend of the punishment type

        return GUIBuilder.builder(messageHandler.get(player, ""), 3)
                .build();
    }
}