package net.astrocube.commons.bukkit.admin.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.admin.chat.StaffChatOptionsMenu;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.core.gui.GUIBuilder;

@Singleton
public class CoreStaffChatOptionsMenu implements StaffChatOptionsMenu {

    private @Inject MessageHandler messageHandler;

    @Override
    public Inventory generateMenu(Player player) {

        GUIBuilder builder = GUIBuilder.builder(
            messageHandler.get(player, "channel.admin.settings.title")
        );

        MenuUtils.generateFrame(builder);

        return null;
    }

}
