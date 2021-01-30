package net.astrocube.api.bukkit.user.display;

import net.astrocube.api.core.virtual.group.Group;

public interface FlairFormatter {

    String format(Group.Flair flair, String playerName);

}