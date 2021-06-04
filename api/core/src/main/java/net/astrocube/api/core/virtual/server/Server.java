package net.astrocube.api.core.virtual.server;

import net.astrocube.api.core.model.ModelProperties;

@ModelProperties.RouteKey("server")
@ModelProperties.Cache
public interface Server extends ServerDoc.Complete {
}