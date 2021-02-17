package net.astrocube.commons.bukkit.admin.chat.option;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.admin.chat.StaffChatOptionsMenu;
import net.astrocube.api.bukkit.admin.chat.StaffOptionCompound;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.bukkit.menu.HeadLibrary;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Singleton
public class StaffChatCompound implements StaffOptionCompound {

    private @Inject MessageHandler messageHandler;
    private @Inject UpdateService<User, UserDoc.Partial> updateService;
    private @Inject StaffChatOptionsMenu staffChatOptionsMenu;

    @Override
    public ItemStack getEnableItem(Player player) {

        ItemStack stack = MenuUtils.generateHead(HeadLibrary.CHAT_ENABLED);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(messageHandler.get(player, "channel.admin.settings.chat.enabled.title"));
        meta.setLore(messageHandler.getMany(player, "channel.admin.settings.chat.enabled.lore"));

        stack.setItemMeta(meta);

        return stack;

    }

    @Override
    public ItemStack getDisableItem(Player player) {

        ItemStack stack = MenuUtils.generateHead(HeadLibrary.CHAT_DISABLED);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(messageHandler.get(player, "channel.admin.settings.chat.disabled.title"));
        meta.setLore(messageHandler.getMany(player, "channel.admin.settings.chat.disabled.lore"));

        stack.setItemMeta(meta);

        return stack;

    }

    @Override
    public AsyncResponse<User> updateOption(User user) {

        user.getSettings().getAdminChatSettings().setActive(
                !user.getSettings().getAdminChatSettings().isActive()
        );

        return updateService.update(user);

    }

}
