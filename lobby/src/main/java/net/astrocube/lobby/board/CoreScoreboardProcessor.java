package net.astrocube.lobby.board;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.board.Board;
import net.astrocube.api.bukkit.board.BoardProvider;
import net.astrocube.api.bukkit.lobby.board.ScoreboardProcessor;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

import java.util.List;

@Singleton
public class CoreScoreboardProcessor implements ScoreboardProcessor {

	private @Inject MessageHandler messageHandler;
	private @Inject FindService<User> findService;
	private @Inject DisplayMatcher displayMatcher;
	private @Inject CloudStatusProvider cloudStatusProvider;
	private @Inject BoardProvider boardProvider;

	@Override
	public void generateBoard(Player player) throws Exception {

		Board board = boardProvider.get(player)
				.orElseGet(() -> boardProvider.create(player, messageHandler.get(player, "lobby.scoreboard.title")));

		User user = findService.findSync(player.getDatabaseId());

		TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(player, user);

		List<String> scoreTranslation = messageHandler.replacingMany(
			player, "lobby.scoreboard.lore",
			"%player%", user.getDisplay(),
			"%rank%", flairFormat.getColor() + flairFormat.getName(),
			"%lobby%", 1,
			"%online%", cloudStatusProvider.getOnline()
		);

		board.setLines(scoreTranslation);

	}

}
