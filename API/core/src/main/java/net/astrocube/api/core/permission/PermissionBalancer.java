package net.astrocube.api.core.permission;

import net.astrocube.api.core.virtual.group.Group;

import java.util.Set;

public interface PermissionBalancer {

    /**
     * Will balance every group weight and permission allowance to
     * determine if the permission can be granted to the user.
     * @param groups to be balanced
     * @param permission to be evaluated
     * @return evaluation of the balance
     */
    boolean evaluate(Set<Group> groups, String permission);

}
