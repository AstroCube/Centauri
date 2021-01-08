package net.astrocube.api.bukkit.channel.admin;

import net.astrocube.api.core.virtual.user.User;

import java.util.Set;

public interface StaffParticipantsProvider {

    Set<User> provideParticipants();

}