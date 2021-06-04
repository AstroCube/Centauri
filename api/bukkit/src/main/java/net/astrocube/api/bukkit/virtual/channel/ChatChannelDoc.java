package net.astrocube.api.bukkit.virtual.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import javax.annotation.Nullable;
import java.util.Set;

public interface ChatChannelDoc {

	interface Creation extends PartialModel {

		String getName();

		int getLifecycle();

		boolean getConfirmation();

		Visibility getVisibility();

		Set<String> getParticipants();

		@Nullable
		String getPermission();

		enum Visibility {
			@JsonProperty("Public") PUBLIC,
			@JsonProperty("Private") PRIVATE,
			@JsonProperty("Permission") PERMISSION
		}

	}

	interface Complete extends Creation, Model.Stamped {
	}

}
