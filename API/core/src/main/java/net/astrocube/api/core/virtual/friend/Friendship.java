package net.astrocube.api.core.virtual.friend;

import net.astrocube.api.core.model.ModelProperties;
import net.astrocube.api.core.virtual.server.ServerDoc;

@ModelProperties.RouteKey("friends")
@ModelProperties.Cache(0)
public interface Friendship extends ServerDoc.Complete {}