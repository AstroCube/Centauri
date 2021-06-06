package net.astrocube.commons.bukkit.admin.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.bukkit.admin.punishment.helper.PunishClickableGenerator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

public class PunishmentReasonChooserMenu {

	private @Inject PunishClickableGenerator punishClickableGenerator;
	private @Inject PresetPunishmentCache presetPunishmentCache;
	private @Inject PunishmentChooserMenu punishmentChooserMenu;
	private @Inject MessageHandler messageHandler;
	private @Inject ShapedMenuGenerator shapedMenuGenerator;

	public Inventory createFilter(Player player, PunishmentBuilder builder) {
		return createChooser(
			player,
			builder,
			presetPunishmentCache.getPunishments(builder.getType()),
			"",
			false
		);
	}

	public Inventory createSearch(Player player, PunishmentBuilder builder, String search) {
		return createChooser(
			player,
			builder,
			presetPunishmentCache.getPunishments()
				.stream()
				.filter(p ->
					messageHandler.get(
						player,
						"punish-menu.reasons." + p.getId() + ".title"
					).contains(search))
				.filter(p -> player.hasPermission("commons.staff.punish.ban") && p.getType() == PunishmentDoc.Identity.Type.BAN)
				.filter(p -> player.hasPermission("commons.staff.punish.kick") && p.getType() == PunishmentDoc.Identity.Type.KICK)
				.filter(p -> player.hasPermission("commons.staff.punish.warn") && p.getType() == PunishmentDoc.Identity.Type.WARN)
				.collect(Collectors.toSet()),
			search,
			true
		);
	}

	private Inventory createChooser(Player player,
									PunishmentBuilder builder, Set<PresetPunishment> preset,
									String criteria, boolean search) {
		return shapedMenuGenerator.generate(
			player,
			messageHandler.get(player, "punishment-expiration-menu.title"),
			() -> player.openInventory(punishmentChooserMenu.createPunishmentChooserMenu(
				player,
				builder.getIssuer(),
				builder.getTarget()
			)),
			PresetPunishment.class,
			preset,
			punishClickableGenerator.buildParser(player, builder, criteria, search)
		);
	}

}