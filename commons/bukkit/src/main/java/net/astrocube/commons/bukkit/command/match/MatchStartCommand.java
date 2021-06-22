package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.game.match.lobby.LobbySessionInvalidatorHelper;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public class MatchStartCommand implements CommandClass {

	private @Inject MessageHandler messageHandler;
	private @Inject CountdownScheduler countdownScheduler;
	private @Inject MatchParticipantsProvider matchParticipantsProvider;
	private @Inject MatchMessageHelper matchMessageHelper;
	private @Inject FindService<GameMode> findService;

	@Command(names = {"start"}, permission = "commons.match.admin")
	public boolean onCommand(@Sender Player player, @OptArg(value = "30") String seconds) {

		Optional<Match> matchOptional = matchMessageHelper.checkInvolvedMatch(player.getDatabaseIdentifier());

		if (matchMessageHelper.getCountAvailability(matchOptional, player)) {
			return true;
		}

		int secondsFixed;
		try {
			secondsFixed = Integer.parseInt(seconds);
		} catch (Exception e) {
			messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.error");
			return true;
		}


		Set<User> involved = matchParticipantsProvider.getMatchPending(matchOptional.get());

		if (involved.size() < 2) {
			messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.insufficient");
			return true;
		}

		findService.find(matchOptional.get().getGameMode())
			.callback(gameModeResponse -> {
				Optional<SubGameMode> subMode = LobbySessionInvalidatorHelper.retrieveSubMode(gameModeResponse, matchOptional.get());

				countdownScheduler.scheduleMatchCountdown(matchOptional.get(), secondsFixed, true, subMode.get());
				involved.addAll(matchParticipantsProvider.getMatchSpectators(matchOptional.get()));
				matchMessageHelper.alertInvolved(involved, matchOptional.get(), player, "game.admin.force");

			});

		return true;
	}

}