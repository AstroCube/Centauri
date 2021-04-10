package net.astrocube.commons.bukkit.admin.chat.option;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.admin.chat.StaffOptionCompound;
import net.astrocube.api.bukkit.menu.HeadLibrary;
import net.astrocube.api.bukkit.menu.MenuUtils;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Singleton
public class StaffSessionCompound implements StaffOptionCompound {

    private @Inject MessageHandler messageHandler;
    private @Inject UpdateService<User, UserDoc.Partial> updateService;

    @Override
    public ItemStack getEnableItem(Player player) {

        ItemStack stack = MenuUtils.generateHead(HeadLibrary.LETTER_GREEN);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(messageHandler.get(player, "channel.admin.settings.logs.enabled.title"));
        meta.setLore(messageHandler.getMany(player, "channel.admin.settings.logs.enabled.lore"));

        stack.setItemMeta(meta);

        return stack;

    }

    @Override
    public ItemStack getDisableItem(Player player) {

        ItemStack stack = MenuUtils.generateHead(HeadLibrary.LETTER_RED);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(messageHandler.get(player, "channel.admin.settings.logs.disabled.title"));
        meta.setLore(messageHandler.getMany(player, "channel.admin.settings.logs.disabled.lore"));

        stack.setItemMeta(meta);

        return stack;

    }

    @Override
    public AsyncResponse<User> updateOption(User user) {

        user.getSettings().getAdminChatSettings().setReadingLogs(
                !user.getSettings().getAdminChatSettings().isReadingLogs()
        );

        return updateService.update(user);

    }

}
