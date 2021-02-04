package net.astrocube.lobby.board;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.lobby.board.ScoreboardProcessor;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftGameBoard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.GameBoard;

import java.util.Collections;

@Singleton
public class CoreScoreboardProcessor implements ScoreboardProcessor {

    private @Inject MessageHandler messageHandler;
    private @Inject FindService<User> findService;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject CloudStatusProvider cloudStatusProvider;

    @Override
    public void generateBoard(Player player) throws Exception {

        User user = findService.findSync(player.getDatabaseIdentifier());
        GameBoard board = new CraftGameBoard(messageHandler.get(player, "lobby.scoreboard.title"));
        StringList scoreTranslation = messageHandler.replacingMany(
                player, "lobby.scoreboard.lore",
                "%%player%%", user.getDisplay(),
                "%%rank%%", DisplayMatcher.getColor(displayMatcher.getRealmDisplay(user)),
                "%%lobby%%", 1,
                "%%online%%", cloudStatusProvider.getOnline()
        );

        Collections.reverse(scoreTranslation);

        for (int i = 0; i < scoreTranslation.size(); i++) {
            board.addLine(i, scoreTranslation.get(i));
        }

        if (player.hasAttachedBoard()) {
            player.removeScoreboard();
        }

        player.setAttachedBoard(board);

    }


}
