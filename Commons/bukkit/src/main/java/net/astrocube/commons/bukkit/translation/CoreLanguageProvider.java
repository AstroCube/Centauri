package net.astrocube.commons.bukkit.translation;

import com.google.inject.Inject;
import me.yushust.message.core.LanguageProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CoreLanguageProvider implements LanguageProvider<Player> {

    public static final String DEFAULT_LANGUAGE = "es";

    private @Inject FindService<User> userFindService;

    @Override
    public String getLanguage(Player player) {
        User user;

        try {
            user = this.userFindService.findSync(player.getDatabaseIdentifier());
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while finding user data for " + player.getName(), ex);
            return DEFAULT_LANGUAGE;
        }
        if (user == null) {
            return DEFAULT_LANGUAGE;
        }

        return user.getLanguage();
    }
}