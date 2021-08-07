package net.astrocube.commons.bukkit.listener.game.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.match.MatchControlSanitizeEvent;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.game.scheduler.RunningMatchBalancer;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.bukkit.util.CountdownTimer;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.commons.bukkit.game.GameControlHelper;
import net.astrocube.commons.bukkit.utils.MessageUtils;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class MatchFinishListener implements Listener {

	private @Inject MatchService matchService;
	private @Inject FindService<Match> findService;
	private @Inject GhostEffectControl ghostEffectControl;
	private @Inject ActualMatchCache actualMatchCache;
	private @Inject RunningMatchBalancer runningMatchBalancer;
	private @Inject GameControlHelper gameControlHelper;
	private @Inject ServerTeleportRetry serverTeleportRetry;
	private @Inject MessageHandler messageHandler;
	private @Inject Plugin plugin;
	private @Inject HttpClient httpClient;
	private @Inject ObjectMapper mapper;

	@EventHandler
	public void onMatchFinish(MatchFinishEvent event) {
		findService.find(event.getMatch()).callback(matchCallback -> {

			try {

				if (!matchCallback.isSuccessful() && !matchCallback.getResponse().isPresent()) {
					throw new GameControlException("Error while obtaining match");
				}

				Match match = matchCallback.getResponse().get();

				if (match.getStatus() != MatchDoc.Status.RUNNING) {
					return;
				}

				matchService.assignVictory(match, event.getWinners());
				Set<Player> players = MatchParticipantsProvider.getOnlinePlayers(match, teamMember -> true);
				// ghostEffectControl.clearMatch(match.getId());
				actualMatchCache.clearSubscriptions(match);

				CountdownTimer timer =
					new CountdownTimer(plugin, 10, (countdownTimer) -> players.forEach(
						player -> MessageUtils.sendActionBar(player, messageHandler.get(player, "game.return.finish-match", "%seconds%", countdownTimer.getSecondsLeft()))
					), () -> players.forEach(
						player -> serverTeleportRetry.attemptGroupTeleport(
							player.getName(),
							plugin.getConfig().getString("server.fallback"),
							1,
							3
						)));

				timer.scheduleTimer();

				runningMatchBalancer.releaseMatch(event.getMatch());

				Optional<GameControlHelper.ModeCompound> compound =
					gameControlHelper.getService(match.getGameMode(), match.getSubMode());

				compound.ifPresent(modeCompound -> Bukkit.getPluginManager().callEvent(
					new MatchControlSanitizeEvent(
						modeCompound.getGameMode(),
						modeCompound.getSubGameMode()
					)
				));

				if (runningMatchBalancer.getTotalMatches() == 1 && runningMatchBalancer.isNeedingRestart()) {
					Bukkit.shutdown();
				}

				httpClient.executeRequestSync("/match/",
					new CoreRequestCallable<>(TypeToken.of(Match.class), mapper),
					new RequestOptions(
						RequestOptions.Type.POST,
						mapper.writeValueAsString(match)
					));

			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "Error while adjudicating match victory", e);
				Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(), false));
			}

		});
	}

}
