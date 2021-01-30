package net.astrocube.commons.bukkit.user.display;

import net.astrocube.api.bukkit.user.display.FlairFormatter;
import net.astrocube.api.core.virtual.group.Group;
import org.bukkit.ChatColor;

public class CoreFlairFormatter implements FlairFormatter {

    @Override
    public String format(Group.Flair flair, String player) {
        ChatColor flairColor = ChatColor.valueOf(flair.getColor().toUpperCase());

        return flairColor + "" + ChatColor.BOLD + flair.getSymbol().toUpperCase() + ChatColor.RESET + " " + player;
    }
}