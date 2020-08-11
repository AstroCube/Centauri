package net.astrocube.commons.core.translation;

import me.yushust.message.core.intercept.MessageInterceptor;
import org.bukkit.ChatColor;

public class ColorMessageInterceptor implements MessageInterceptor {

    @Override
    public String intercept(String text) {
        return text
                .replace("%n%", "")
                .replace("%%black%%", ChatColor.BLACK + "")
                .replace("%%dark_blue%%", ChatColor.DARK_BLUE + "")
                .replace("%%dark_green%%", ChatColor.DARK_GREEN + "")
                .replace("%%dark_aqua%%", ChatColor.DARK_AQUA + "")
                .replace("%%dark_red%%", ChatColor.DARK_RED + "")
                .replace("%%dark_purple%%", ChatColor.DARK_PURPLE + "")
                .replace("%%gold%%", ChatColor.GOLD + "")
                .replace("%%gray%%", ChatColor.GRAY + "")
                .replace("%%dark_gray%%", ChatColor.DARK_GRAY + "")
                .replace("%%blue%%", ChatColor.BLUE + "")
                .replace("%%green%%", ChatColor.GREEN + "")
                .replace("%%aqua%%", ChatColor.AQUA + "")
                .replace("%%red%%", ChatColor.RED + "")
                .replace("%%light_purple%%", ChatColor.LIGHT_PURPLE + "")
                .replace("%%yellow%%", ChatColor.YELLOW + "")
                .replace("%%white%%", ChatColor.WHITE + "")
                .replace("%%obfuscated%%", ChatColor.MAGIC + "")
                .replace("%%bold%%", ChatColor.BOLD + "")
                .replace("%%strike%%", ChatColor.STRIKETHROUGH + "")
                .replace("%%underline%%", ChatColor.UNDERLINE + "")
                .replace("%%italic%%", ChatColor.ITALIC + "")
                .replace("%%reset%%", ChatColor.RESET + "");
    }

}
