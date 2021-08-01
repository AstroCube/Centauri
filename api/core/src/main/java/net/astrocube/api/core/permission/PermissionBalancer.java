package net.astrocube.api.core.permission;

import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface PermissionBalancer {

    /**
     * Will balance every group weight and permission allowance to
     * determine if the permission can be granted to the user.
     *
     * @param groups     to be balanced
     * @param permission to be evaluated
     * @return evaluation of the balance
     */
    boolean evaluate(Collection<Group> groups, String permission);

	/**
	 * Determines if the permission can be granted to the user, assumes
	 * that the given {@code groups} are already ordered by priority
	 *
	 * @param groups     to be balanced
	 * @param permission to be evaluated
	 * @return evaluation of the balance
	 */
    boolean evaluateSorted(List<Group> groups, String permission);

    /**
     * Check if user has permissions unlinked from a permissible
     *
     * @param user       to be checked
     * @param balancer   to be used
     * @param permission to use
     * @return if user has permission
     */
    static boolean hasUnlinkedPermission(User user, PermissionBalancer balancer, String permission) {
        return balancer.evaluateSorted(
                user.getGroups().stream()
                        .map(UserDoc.UserGroup::getGroup)
                        .filter(g -> !g.getPermissions().isEmpty())
						.sorted(Comparator.comparing(Group::getPriority))
                        .collect(Collectors.toList()),
				permission
        );
    }

}
