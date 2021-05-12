package net.astrocube.lobby.hotbar.collection;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.item.type.ItemBuilder;

public class ProfileGadgetStack {

	public static ItemStack get(MessageHandler provider, Player player) {
		return NBTUtils.addString(
			ItemBuilder.newSkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
				.setOwner(player.getName())
				.setName(provider.get(player, "lobby.profile.gadget"))
				.setLore(provider.getMany(player, "lobby.profile.gadget-lore"))
				.build(),
			"actionable",
			"profile_menu"
		);
	}

}
