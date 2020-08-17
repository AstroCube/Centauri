package net.astrocube.api.core.permission;

import net.astrocube.api.core.virtual.group.Group;

import java.util.Map;

public interface EffectivePermissionsExtractor {

    /**
     * Extract effective permissions map from group
     * @param group to be extracted
     * @return evaluable set
     */
    Map<String, Boolean> extractFromGroup(Group group);

}
