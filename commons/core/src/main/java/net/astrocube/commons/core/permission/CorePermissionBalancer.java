package net.astrocube.commons.core.permission;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.permission.PermissionBalancer;
import net.astrocube.api.core.permission.PermissionEvaluator;
import net.astrocube.api.core.virtual.group.Group;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class CorePermissionBalancer implements PermissionBalancer {

	private @Inject PermissionEvaluator permissionEvaluator;

	@Override
	public boolean evaluate(Collection<Group> groups, String permission) {
		List<Group> ordered = new ArrayList<>(groups);
		ordered.sort(Comparator.comparing(Group::getPriority));
		return evaluateSorted(ordered, permission);
	}

	@Override
	public boolean evaluateSorted(List<Group> groups, String permission) {

		short evaluatedPriority = -1;
		boolean granted = false;

		for (Group group : groups) {

            /*
                Remember that second value of array will determine if permission is explicitly denied
             */
			boolean[] evaluable = this.permissionEvaluator.evaluate(group, permission);

			if (evaluatedPriority == -1 || evaluatedPriority < group.getPriority()) {
				evaluatedPriority = group.getPriority();
				if (granted) {
					if (evaluable[1]) {
						granted = false;
					}
				} else {
					if (evaluable[0]) {
						granted = true;
					}
				}
			}
		}

		return granted;

	}
}
