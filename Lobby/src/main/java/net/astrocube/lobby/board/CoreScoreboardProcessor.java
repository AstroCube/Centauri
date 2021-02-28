package net.astrocube.lobby.board;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;
import net.astrocube.api.bukkit.lobby.board.ScoreboardProcessor;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import team.unnamed.uboard.builder.ScoreboardBuilder;

@Singleton
public class CoreScoreboardProcessor implements ScoreboardProcessor {

    private @Inject MessageHandler messageHandler;
    private @Inject FindService<User> findService;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject CloudStatusProvider cloudStatusProvider;
    private @Inject ScoreboardManagerProvider scoreboardManagerProvider;

    @Override
    public void generateBoard(Player player) throws Exception {

        ScoreboardBuilder builder = scoreboardManagerProvider.getScoreboard().newScoreboard(player.getDatabaseIdentifier());
        builder.setTitle(messageHandler.get(player, "lobby.scoreboard.title"));

        User user = findService.findSync(player.getDatabaseIdentifier());

        TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(player, user);

        StringList scoreTranslation = messageHandler.replacingMany(
                player, "lobby.scoreboard.lore",
                "%%player%%", user.getDisplay(),
                "%%rank%%", flairFormat.getColor() + flairFormat.getName(),
                "%%lobby%%", 1,
                "%%online%%", cloudStatusProvider.getOnline()
        );

        scoreTranslation.forEach(builder::addLine);
        scoreboardManagerProvider.getScoreboard().setToPlayer(player, builder.build());

    }

}