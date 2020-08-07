package net.astrocube.api.core.virtual.group;


import net.astrocube.api.core.model.ModelProperties;
import net.astrocube.api.core.model.PartialModel;

import java.util.Set;

@ModelProperties.RouteKey("group")
@ModelProperties.Cache()
public interface Group extends PartialModel {

    String getName();

    short getPriority();

    String getColor();

    Set<String> getFlairs();

    Set<String> getPermissions();

    String getLink();

    boolean isStaff();

    String getDiscordRole();

    interface Flair {

        String getRealm();

        String getColor();

        String getSymbol();

    }

}
