package net.astrocube.commons.core.permission;

import com.google.inject.Singleton;
import net.astrocube.api.core.permission.EffectivePermissionsExtractor;
import net.astrocube.api.core.virtual.group.Group;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreEffectivePermissionsExtractor implements EffectivePermissionsExtractor {

	@Override
	public Map<String, Boolean> extractFromGroup(Group group) {

		Map<String, Boolean> effectivePermissions = new HashMap<>();

		for (String permission : group.getPermissions()) {

			if (permission.isEmpty()) {
				continue;
			}

			boolean negated = permission.charAt(0) == '-';
			effectivePermissions.put(
				negated ? permission.substring(1) : permission,
				negated
			);
		}

		return effectivePermissions;
	}

}
