package net.astrocube.api.bukkit.user.staff;

import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;

import java.util.Set;

public interface OnlineStaffProvider {

    Set<User> provide() throws Exception;

    Set<Group> getGroups() throws Exception;

    Set<User> getFromGroup(Set<String> group) throws Exception;

    Set<User> getFromGroup(String group) throws Exception;

}