package net.astrocube.api.bukkit.translation;

import com.google.inject.Inject;
import me.yushust.message.language.Linguist;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.logging.Level;

public class CoreLanguageProvider implements Linguist<Player> {

	private @Inject FindService<User> userFindService;

	@Override
	@Nullable
	public String getLanguage(Player player) {
		User user;

		try {
			user = this.userFindService.findSync(player.getDatabaseId());
		} catch (Exception ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Error while finding user data for " + player.getName(), ex);
			return null;
		}

		if (user == null) {
			return null;
		}

		return user.getLanguage();
	}
}