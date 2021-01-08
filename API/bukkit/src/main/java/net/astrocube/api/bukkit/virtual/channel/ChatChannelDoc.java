package net.astrocube.api.bukkit.virtual.channel;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import java.util.Optional;
import java.util.Set;

public interface ChannelDoc {

    interface Creation extends PartialModel {

        String getName();

        int getLifecycle();

        boolean getConfirmation();

        Visibility getVisibility();

        Set<String> getParticipants();

        Optional<String> getPermission();

        enum Visibility {
            PUBLIC, PRIVATE, PERMISSIONS
        }

    }

    interface Complete extends Creation, Model.Stamped {}

}
