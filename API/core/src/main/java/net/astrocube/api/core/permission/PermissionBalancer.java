package net.astrocube.api.core.permission;

import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;

import java.util.Set;
import java.util.stream.Collectors;

public interface PermissionBalancer {

	/**
	 * Will balance every group weight and permission allowance to
	 * determine if the permission can be granted to the user.
	 * @param groups     to be balanced
	 * @param permission to be evaluated
	 * @return evaluation of the balance
	 */
	boolean evaluate(Set<Group> groups, String permission);

	/**
	 * Check if user has permissions unlinked from a permissible
	 * @param user     to be checked
	 * @param balancer to be used
	 * @param s        to use
	 * @return if user has permission
	 */
	static boolean hasUnlinkedPermission(User user, PermissionBalancer balancer, String s) {
		return balancer.evaluate(
			user.getGroups().stream()
				.map(UserDoc.UserGroup::getGroup)
				.filter(g -> !g.getPermissions().isEmpty())
				.collect(Collectors.toSet()), s
		);
	}

}
