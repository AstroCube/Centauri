package net.astrocube.commons.core.permission;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.permission.PermissionBalancer;
import net.astrocube.api.core.permission.PermissionEvaluator;
import net.astrocube.api.core.virtual.group.Group;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CorePermissionBalancer implements PermissionBalancer {

	private @Inject PermissionEvaluator permissionEvaluator;

	@Override
	public boolean evaluate(Set<Group> groups, String permission) {

		List<Group> ordered = groups
			.stream()
			.sorted(Comparator.comparing(Group::getPriority))
			.collect(Collectors.toList());

		short evaluatedPriority = -1;
		boolean granted = false;

		for (Group group : ordered) {

            /*
                Remember that second value of array will determine if permission is explicitly denied
             */
			boolean[] evaluable = this.permissionEvaluator.evaluate(group, permission);

			if (evaluatedPriority == -1 || evaluatedPriority < group.getPriority()) {
				evaluatedPriority = group.getPriority();
				if (granted) {
					if (evaluable[1]) granted = false;
				} else {
					if (evaluable[0]) granted = true;
				}
			}

		}

		return granted;

	}
}
