package net.astrocube.api.bukkit.channel.admin;

import net.astrocube.api.core.virtual.user.User;

public interface StaffMessageManager {

    void sendMessage(String message, User sender, boolean important);

}