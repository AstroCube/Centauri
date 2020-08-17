package net.astrocube.api.core.permission;

import net.astrocube.api.core.virtual.group.Group;

public interface PermissionEvaluator {

    /**
     * Evaluate if a certain permission is allowed by a group
     * @param group to be evaluated
     * @param permission to be granted
     * @return result of evaluation
     */
    boolean[] evaluate(Group group, String permission);

}
