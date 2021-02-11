package net.astrocube.commons.bukkit.admin.selector.item;

import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;

public interface GameModeItemExtractor {

    ItemClickable generateGameMode(GameMode gameMode, Player player);
}