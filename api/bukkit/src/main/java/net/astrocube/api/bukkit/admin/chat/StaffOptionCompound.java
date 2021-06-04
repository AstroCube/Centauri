package net.astrocube.api.bukkit.admin.chat;

import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface StaffOptionCompound {

	ItemStack getEnableItem(Player player);

	ItemStack getDisableItem(Player player);

	AsyncResponse<User> updateOption(User user);

}
