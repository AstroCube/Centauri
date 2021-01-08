package net.astrocube.api.bukkit.user.staff;

import net.astrocube.api.core.virtual.user.User;

import java.util.Set;

public interface OnlineStaffProvider {

    Set<User> provide() throws Exception;

}