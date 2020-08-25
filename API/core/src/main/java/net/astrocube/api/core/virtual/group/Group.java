package net.astrocube.api.core.virtual.group;


import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelProperties;
import net.astrocube.api.core.model.PartialModel;

import java.util.Set;

@ModelProperties.RouteKey("group")
public interface Group extends Model.Stamped {

    String getName();

    short getPriority();

    @JsonProperty("html_color")
    String getColor();

    @JsonProperty("minecraft_flair")
    Set<Flair> getFlairs();

    @JsonProperty("minecraft_permissions")
    Set<String> getPermissions();

    @JsonProperty("badge_link")
    String getLink();

    boolean isStaff();

    @JsonProperty("discord_role")
    String getDiscordRole();

    interface Flair {

        String getRealm();

        String getColor();

        String getSymbol();

    }

}
