package net.astrocube.api.bukkit.virtual.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import java.util.Map;
import java.util.Set;

public interface ChatChannelMessageDoc {

    interface Creation extends PartialModel {

        String getSender();

        String getMessage();

        String getChannel();

        Origin getOrigin();

        Map<String, Object> getMeta();

        enum Origin {
            @JsonProperty("InGame") INGAME,
            @JsonProperty("Website") WEBSITE
        }
    }

    interface Complete extends Creation, Model.Stamped {

        Set<String> getViewed();

    }

}