package net.astrocube.api.bukkit.virtual.game.match;

import net.astrocube.api.core.model.ModelProperties;

@ModelProperties.RouteKey("match")
@ModelProperties.Cache(value = -1)
public interface Match extends MatchDoc.Complete {
}
