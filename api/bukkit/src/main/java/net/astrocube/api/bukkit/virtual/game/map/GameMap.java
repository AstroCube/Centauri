package net.astrocube.api.bukkit.virtual.game.map;

import net.astrocube.api.core.model.ModelProperties;

/**
 * Map record created at database which store
 * info to work correctly along Game Control.
 */
@ModelProperties.Cache(3600)
@ModelProperties.RouteKey("map")
public interface GameMap extends GameMapDoc.Complete {
}
