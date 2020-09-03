package net.astrocube.api.core.virtual.gamemode;

import net.astrocube.api.core.model.ModelProperties;
import net.astrocube.api.core.virtual.user.UserDoc;

@ModelProperties.RouteKey("gamemode")
@ModelProperties.Cache
public interface GameMode extends GameModeDoc.Complete {
}
