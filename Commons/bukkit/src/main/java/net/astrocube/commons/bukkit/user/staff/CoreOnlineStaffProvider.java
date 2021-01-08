package net.astrocube.commons.bukkit.channel.admin;

import net.astrocube.api.bukkit.channel.admin.StaffParticipantsProvider;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;

import javax.inject.Inject;
import java.util.Set;

public class CoreStaffParticipantsProvider implements StaffParticipantsProvider {

    private @Inject QueryService<User> queryService;

    @Override
    public Set<User> provideParticipants() {
        return null;
    }
}