package net.astrocube.api.bukkit.nametag.packet;

import java.util.Collection;

public interface TeamPacketBuilder {

    /** Sets the team name, always required */
    TeamPacketBuilder setTeamName(String name);

    /** Sets the packet action, always required */
    TeamPacketBuilder setAction(TeamAction action);

    /** Sets the team members (to add, to remove or initial team members) */
    TeamPacketBuilder setMembers(Collection<String> members);

    /** Sets the team prefix, used only on CREATE and UPDATE */
    TeamPacketBuilder setPrefix(String prefix);

    /** Sets the team suffix, used only on CREATE and UPDATE*/
    TeamPacketBuilder setSuffix(String suffix);

    TeamPacketBuilder setVisibility(Visibility visibility);

    /** Sets the packet pack option data, used only on CREATE */
    TeamPacketBuilder setPackOptionData(int packOptionData);

    /** Builds the packet with the previously specified properties */
    Object build();

    enum Visibility {
        ALWAYS,
        NEVER
    }

    enum TeamAction {
        CREATE,
        REMOVE,
        UPDATE,
        ADD_MEMBERS,
        REMOVE_MEMBERS
    }

}