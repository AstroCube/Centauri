package net.astrocube.api.bukkit.user.profile;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class CoreGameProfile implements AbstractGameProfile {

	private final UUID id;
	private final String name;
	private final Set<AbstractProperty> properties;

	public CoreGameProfile(UUID id, String name) {
		this.id = id;
		this.name = name;
		this.properties = new HashSet<>();
	}

}
