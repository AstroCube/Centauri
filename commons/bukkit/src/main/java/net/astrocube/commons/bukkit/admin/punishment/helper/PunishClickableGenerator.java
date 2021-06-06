package net.astrocube.commons.bukkit.admin.punishment.helper;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.commons.bukkit.admin.punishment.PunishmentExpirationChooserMenu;
import net.astrocube.commons.core.utils.PrettyTimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PunishClickableGenerator {

	private @Inject PunishmentExpirationChooserMenu punishmentExpirationChooserMenu;
	private @Inject MessageHandler messageHandler;

	public Function<PresetPunishment, ItemClickable> buildParser(Player player,
																 PunishmentBuilder punishmentBuilder,
																 String criteria,
																 boolean search) {
		return punishment -> {
			String translated = messageHandler.get(player, "punish-menu.reasons." + punishment.getId() + ".reason");
			return ItemClickable.builder()
				.setItemStack(ItemBuilder.newBuilder(Material.PAPER)
					.setName(
						ChatColor.AQUA + messageHandler.get(
							player, "punish-menu.reasons." + punishment.getId() + ".title"
						) + ChatColor.GRAY + (search ? " - " + messageHandler.get(
							player, "punish-menu.type." + punishment.getType().toString().toLowerCase()
						) : "")
					)
					.setLore(messageHandler.replacingMany(
						player, "punish-menu.lore",
						"%reason%", translated,
						"%expire%", punishment.getExpiration() == -1 ? messageHandler.get(
							player, "punishment-expiration.never"
						) : PrettyTimeUtils.getHumanDate(PunishmentHandler.generateFromExpiration(
							punishment.getExpiration()
						), punishmentBuilder.getIssuer().getLanguage())
					))
					.build()
				)
				.setAction(event -> {
					if (search) {
						punishmentBuilder.setType(punishment.getType());
					}

					punishmentBuilder.setReason(translated);
					punishmentBuilder.setDuration(punishment.getExpiration());
					player.openInventory(punishmentExpirationChooserMenu.createPunishmentExpirationChooserMenu(
						player, punishmentBuilder, criteria, search
					));

					return true;
				})
				.build();
		};
	}

}