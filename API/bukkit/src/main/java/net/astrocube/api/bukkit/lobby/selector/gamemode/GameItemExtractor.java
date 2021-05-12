package net.astrocube.api.bukkit.lobby.selector.gamemode;

import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;

public interface GameItemExtractor {

	/**
	 * Generate {@link ItemClickable} from {@link GameMode} to be used the Game Selector
	 * @param gameModeDoc to be extracted
	 * @param player      from where translation will be found
	 * @return clickable item
	 */
	ItemClickable generate(GameMode gameModeDoc, Player player);

}
