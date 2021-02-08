package net.astrocube.api.core.virtual.group;


import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelProperties;
import net.astrocube.api.core.virtual.user.UserDoc;

import java.util.Set;
import java.util.stream.Collectors;

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

    static int getLowestPriority(Set<UserDoc.UserGroup> groups) {

        Set<Group> groupSet = groups.stream().map(UserDoc.UserGroup::getGroup).collect(Collectors.toSet());

        int priority = -1;

        for (Group group : groupSet) {
            if (priority == -1 || group.getPriority() < priority) {
                priority = group.getPriority();
            }
        }

        return priority;
    }

}
