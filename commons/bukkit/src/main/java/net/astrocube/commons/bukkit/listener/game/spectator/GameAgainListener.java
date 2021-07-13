package net.astrocube.commons.bukkit.listener.game.spectator;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequester;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class GameAgainListener implements Listener {

	@Inject
	private ActualMatchCache actualMatchCache;

	@Inject
	private MatchmakingRequester matchmakingRequester;

	@EventHandler
	public void onGameGadgetInteract(ActionableItemEvent event) throws Exception {

		Player player = event.getPlayer();

		if (event.getAction().equals("gc_game_again") && (event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)) {
			actualMatchCache.get(player.getDatabaseIdentifier()).ifPresent(match -> matchmakingRequester.execute(player, match.getGameMode(), match.getSubMode()));
		}
	}

}
