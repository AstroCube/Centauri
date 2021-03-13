package net.astrocube.commons.bukkit.admin.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.admin.chat.StaffChatOptionsMenu;
import net.astrocube.api.bukkit.admin.chat.StaffOptionCompound;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.admin.chat.option.StaffChatCompound;
import net.astrocube.commons.bukkit.admin.chat.option.StaffPunishCompound;
import net.astrocube.commons.bukkit.admin.chat.option.StaffSessionCompound;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.menu.MenuUtils;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;

@Singleton
public class CoreStaffChatOptionsMenu implements StaffChatOptionsMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject GenericHeadHelper headHelper;
    private @Inject FindService<User> findService;

    private @Inject StaffChatCompound staffChatCompound;
    private @Inject StaffPunishCompound staffPunishCompound;
    private @Inject StaffSessionCompound staffSessionCompound;


    @Override
    public void generateMenu(Player player) {

        findService.find(player.getDatabaseIdentifier()).callback(userCallback -> {

            if (!userCallback.isSuccessful()) {
                messageHandler.sendIn(player, AlertModes.ERROR, "channel.admin.settings.error");
            }

            userCallback.ifSuccessful(user -> {

                GUIBuilder builder = GUIBuilder.builder(
                        messageHandler.get(player, "channel.admin.settings.title")
                );

                builder.addItem(generateClickable(staffChatCompound, player, user, 21, user.getSettings().getAdminChatSettings().isActive()));
                builder.addItem(generateClickable(staffPunishCompound, player, user, 22, user.getSettings().getAdminChatSettings().isReadingPunishments()));
                builder.addItem(generateClickable(staffSessionCompound, player, user, 23, user.getSettings().getAdminChatSettings().isReadingLogs()));
                builder.addItem(
                        headHelper.generateDefaultClickable(headHelper.backButton(player), 31, ClickType.LEFT, HumanEntity::closeInventory)
                );

                MenuUtils.generateFrame(builder);
                player.openInventory(builder.build());
            });

        });

    }

    private ItemClickable generateClickable(StaffOptionCompound compound, Player player, User user, int slot, boolean active) {

        ItemStack stack = compound.getEnableItem(player);

        if (!active) {
            stack = compound.getDisableItem(player);
        }

        return headHelper.generateDefaultClickable(stack, slot, ClickType.LEFT, (p) ->
                compound.updateOption(user).callback(updatedUserCallback -> {

                    if (!updatedUserCallback.isSuccessful()) {
                        messageHandler.get(player, "channel.admin.settings.error-update");
                    }

                    updatedUserCallback.ifSuccessful(updatedUser -> {
                        p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
                        player.closeInventory();
                        generateMenu(p);
                    });

                })
        );
    }

}
