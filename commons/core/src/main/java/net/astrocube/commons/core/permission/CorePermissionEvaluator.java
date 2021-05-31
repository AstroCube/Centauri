package net.astrocube.commons.core.permission;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.permission.EffectivePermissionsExtractor;
import net.astrocube.api.core.permission.PermissionEvaluator;
import net.astrocube.api.core.virtual.group.Group;

import java.util.Map;

@Singleton
public class CorePermissionEvaluator implements PermissionEvaluator {

	private @Inject EffectivePermissionsExtractor effectivePermissionsExtractor;

	@Override
	public boolean[] evaluate(Group group, String permission) {

		String[] requestedPermissionTree = permission.split("\\.");

		for (
			Map.Entry<String, Boolean> entry :
			this.effectivePermissionsExtractor.extractFromGroup(group).entrySet()
		) {

			if (entry.getKey().equalsIgnoreCase(permission))
				return new boolean[]{entry.getValue(), entry.getValue()};

			int scanningLength = requestedPermissionTree.length;
			String[] permissionTree = entry.getKey().split("\\.");

			if (permissionTree.length < scanningLength) scanningLength = permissionTree.length;

			for (int i = 0; i < scanningLength; i++) {
				if (permissionTree[i].equalsIgnoreCase("*"))
					return new boolean[]{entry.getValue(), entry.getValue()};
				if (!requestedPermissionTree[i].equalsIgnoreCase(permissionTree[i])) break;
			}
		}

		return new boolean[]{false, false};

	}

}
