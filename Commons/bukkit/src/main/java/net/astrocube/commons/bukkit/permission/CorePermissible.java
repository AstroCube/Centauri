package net.astrocube.commons.bukkit.permission;

import net.astrocube.api.core.permission.PermissionBalancer;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;

import java.util.logging.Level;
import java.util.stream.Collectors;

public class CorePermissible extends PermissibleBase {

	private final FindService<User> findService;
	private final PermissionBalancer balancer;
	private final Player player;

	public CorePermissible(Player player, FindService<User> findService, PermissionBalancer balancer) {
		super(player);
		this.player = player;
		this.findService = findService;
		this.balancer = balancer;
	}

	@Override
	public boolean hasPermission(String s) {
		try {
			User user = findService.findSync(player.getDatabaseIdentifier());
			return balancer.evaluate(
				user.getGroups().stream()
					.map(UserDoc.UserGroup::getGroup)
					.filter(g -> !g.getPermissions().isEmpty())
					.collect(Collectors.toSet()), s
			);
		} catch (Exception exception) {
			Bukkit.getLogger().log(Level.SEVERE, "An exception occurred while getting player " + player.getName() + " permissions", exception);
		}
		return false;
	}

}
