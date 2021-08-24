package net.astrocube.lobby.hide;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.hide.HideCompoundMatcher;
import net.astrocube.api.bukkit.lobby.hide.HideJoinProcessor;
import net.astrocube.api.bukkit.lobby.hide.HideStatusModifier;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class CoreHideJoinProcessor implements HideJoinProcessor {

	private @Inject HideStatusModifier hideStatusModifier;
	private @Inject HideCompoundMatcher hideCompoundMatcher;
	private @Inject Plugin plugin;
	private @Inject FindService<User> findService;

	@Override
	public void process(User user) {

		if (user.getSettings().getGeneralSettings().isHidingPlayers()) {
			hideStatusModifier.globalApply(user);
		}

		Player userPlayer = Bukkit.getPlayer(user.getUsername());

		Bukkit.getOnlinePlayers().forEach(player -> {
			if (!player.getDatabaseId().equalsIgnoreCase(user.getId())) {
				findService.find(player.getDatabaseId()).callback(userResponse -> {

					if (userResponse.isSuccessful() && userResponse.getResponse().isPresent()) {
						User target = userResponse.getResponse().get();
						if (target.getSettings().getGeneralSettings().isHidingPlayers()) {
							hideStatusModifier.individualApply(
								userResponse.getResponse().get(),
								user,
								hideCompoundMatcher.getUserCompound(userResponse.getResponse().get())
							);
						}
					} else {
						Bukkit.getScheduler().runTask(plugin, () -> player.hidePlayer(userPlayer));
					}

				});
			}
		});

	}

}
