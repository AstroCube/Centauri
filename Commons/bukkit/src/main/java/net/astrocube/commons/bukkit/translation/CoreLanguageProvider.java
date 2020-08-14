package net.astrocube.commons.bukkit.translation;

import com.google.inject.Inject;
import me.yushust.message.core.LanguageProvider;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CoreLanguageProvider implements LanguageProvider<Player> {

    private @Inject FindService<User> userFindService;

    @Override
    public String getLanguage(Player player) {

        User user;

        try {
            user = userFindService.findSync(player.getDatabaseIdentifier());
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while finding user data for " + player.getName(), e);
            return MessageProvider.DEFAULT_LANGUAGE;
        }

        if (user == null) {
            return MessageProvider.DEFAULT_LANGUAGE;
        }

        return user.getLanguage();

    }

}
