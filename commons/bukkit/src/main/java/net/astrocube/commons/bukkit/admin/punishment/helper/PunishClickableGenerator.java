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

import javax.inject.Inject;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PunishClickableGenerator {

	private @Inject PunishmentExpirationChooserMenu punishmentExpirationChooserMenu;
	private @Inject MessageHandler messageHandler;

	public Set<ShapedMenuGenerator.BaseClickable> buildPunishReasons
		(Player player, PunishmentBuilder punishmentBuilder, Set<PresetPunishment> punishments, String criteria, boolean search) {
		return punishments.stream()
			.map(punishment -> {

				String translated = messageHandler.get(player, "punish-menu.reasons." + punishment.getId() + ".reason");
				ItemStack paper = new ItemStack(Material.PAPER);
				ItemMeta meta = paper.getItemMeta();

				meta.setDisplayName(
					ChatColor.AQUA +
						messageHandler.get(player, "punish-menu.reasons." + punishment.getId() + ".title") +
						ChatColor.GRAY + (search ? " - " + messageHandler.get(player, "punish-menu.type." + punishment.getType().toString().toLowerCase()) : "")
				);

				meta.setLore(
					messageHandler.replacingMany(
						player, "punish-menu.lore",
						"%reason%", translated,
						"%expire%", punishment.getExpiration() == -1 ? messageHandler.get(player, "punishment-expiration.never") :
							PrettyTimeUtils.getHumanDate(
								Objects.requireNonNull(PunishmentHandler.generateFromExpiration(
									punishment.getExpiration()
								)),
								punishmentBuilder.getIssuer().getLanguage()
							)
					)
				);

				paper.setItemMeta(meta);

				return new ShapedMenuGenerator.BaseClickable() {
					@Override
					public Consumer<Player> getClick() {
						return (p) -> {
							if (search) {
								punishmentBuilder.setType(punishment.getType());
							}
							punishmentBuilder.setReason(translated);
							punishmentBuilder.setDuration(punishment.getExpiration());
							player.openInventory(punishmentExpirationChooserMenu.createPunishmentExpirationChooserMenu(player, punishmentBuilder, criteria, search));
						};
					}

					@Override
					public ItemStack getStack() {
						return paper;
					}
				};


			}).collect(Collectors.toSet());
	}

}