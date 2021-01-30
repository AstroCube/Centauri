package net.astrocube.api.bukkit.game.map.configuration;

import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.HashSet;
import java.util.Set;

public class SizedTeam implements MatchDoc.Team {

    private final int capacity;
    private final String name;
    private final String color;
    private final Set<MatchDoc.TeamMember> members;

    public SizedTeam(String name, String color, int capacity) {
        this.name = name;
        this.color = color;
        this.capacity = capacity;
        this.members = new HashSet<>();
    }

    @Override
    public Set<MatchDoc.TeamMember> getMembers() {
        return members;
    }

    public void addMember(MatchDoc.TeamMember member) {
        members.add(member);
    }

    public boolean isFull() {
        return members.size() >= capacity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Team members: ");

        getMembers().forEach(member -> sb.append(member.getUser()).append(", "));

        sb.append("Full: ").append(isFull());

        return sb.toString();

    }

}
