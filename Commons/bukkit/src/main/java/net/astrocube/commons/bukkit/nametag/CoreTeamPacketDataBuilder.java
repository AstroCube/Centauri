package net.astrocube.commons.bukkit.nametag;

import net.astrocube.api.bukkit.nametag.packet.TeamPacketBuilder;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Represents a fake packet data serializer or a
 * "packet data writer", it tells the packet that
 * it should read the properties from this serializer.
 * This avoids the usage of reflection to set the packet
 * properties.
 */
public class CoreTeamPacketDataBuilder
        extends PacketDataSerializer
        implements TeamPacketBuilder {

    /**
     * Lookup array to check what fields were
     * read by the packet using this fake packet
     * data serializer.
     */
    private final boolean[] readsLookup = new boolean[7];

    private static final int
            NAME_INDEX = 0,
            DISPLAY_NAME_INDEX = 1,
            PREFIX_INDEX = 2,
            SUFFIX_INDEX = 3,
            PACK_OPTION_DATA_INDEX = 4,
            VISIBILITY_INDEX = 5,
            ACTION_INDEX = 6;

    private String name;
    private String prefix;
    private String suffix;
    private int packOptionData;
    private TeamAction action;
    private Visibility visibility;

    private Collection<String> members;
    private Iterator<String> memberIterator;

    public CoreTeamPacketDataBuilder() {
        super(null);
    }

    @Override
    public TeamPacketBuilder setTeamName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TeamPacketBuilder setAction(TeamAction action) {
        this.action = action;
        return this;
    }

    @Override
    public TeamPacketBuilder setMembers(Collection<String> members) {
        this.members = members;
        this.memberIterator = members.iterator();
        return this;
    }

    @Override
    public TeamPacketBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public TeamPacketBuilder setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    @Override
    public TeamPacketBuilder setVisibility(Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    @Override
    public TeamPacketBuilder setPackOptionData(int packOptionData) {
        this.packOptionData = packOptionData;
        return this;
    }

    @Override
    public Object build() {

        if (name == null || action == null || visibility == null) {
            throw new IllegalStateException("The field 'name', field 'action' and field 'visibility' are required!");
        }

        // Just check the first element, if read, reset the lookup array
        if (readsLookup[0]) {
            reset();
        }

        Packet<?> packet = new PacketPlayOutScoreboardTeam();

        try {
            packet.a(this);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to write packet properties", e);
            throw new IllegalStateException("Failed to write packet properties", e);
        }

        return packet;
    }

    @Override
    public byte readByte() {

        // action read
        if (!readsLookup[ACTION_INDEX]) {
            readsLookup[ACTION_INDEX] = true;
            return (byte) action.ordinal();
        }

        // pack option data read
        if (!readsLookup[PACK_OPTION_DATA_INDEX]) {
            readsLookup[PACK_OPTION_DATA_INDEX] = true;
            return (byte) packOptionData;
        }

        // this shouldn't happen
        return 0;
    }

    // The e() method is used only for read the changed members
    @Override
    public int e() {
        return members.size();
    }

    @Override
    public String c(int i) {

        if (!readsLookup[NAME_INDEX] && i == 16) {
            readsLookup[NAME_INDEX] = true;
            return name;
        }

        if (
                action == TeamAction.CREATE
                        || action == TeamAction.UPDATE
        ) {
            // read the display name, always same as name
            if (!readsLookup[DISPLAY_NAME_INDEX] && i == 32) {
                readsLookup[DISPLAY_NAME_INDEX] = true;
                return name;
            }

            // read the prefix
            if (!readsLookup[PREFIX_INDEX] && i == 16) {
                readsLookup[PREFIX_INDEX] = true;
                return prefix;
            }

            // read the suffix
            if (!readsLookup[SUFFIX_INDEX] && i == 16) {
                readsLookup[SUFFIX_INDEX] = true;
                return suffix;
            }

            // read the visibility, always "always"
            if (!readsLookup[VISIBILITY_INDEX] && i == 32) {
                readsLookup[VISIBILITY_INDEX] = true;
                switch (visibility) {
                    case NEVER:
                        return ScoreboardTeamBase.EnumNameTagVisibility.NEVER.e;
                    case ALWAYS:
                        return ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e;
                }

            }

            // read the members
            if (action == TeamAction.CREATE && i == 40 && memberIterator.hasNext()) {
                return memberIterator.next();
            }
        } else if ((action == TeamAction.ADD_MEMBERS || action == TeamAction.REMOVE_MEMBERS) && i == 40
                && memberIterator.hasNext()) {
            return memberIterator.next();
        }
        return "";
    }

    private void reset() {
        // Mark everything as not read
        Arrays.fill(readsLookup, false);
        // Re-define the iterator
        this.memberIterator = this.members.iterator();
    }

}