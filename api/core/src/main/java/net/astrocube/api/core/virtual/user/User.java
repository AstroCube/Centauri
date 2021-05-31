package net.astrocube.api.core.virtual.user;

import net.astrocube.api.core.model.ModelProperties;

@ModelProperties.RouteKey("user")
@ModelProperties.Cache
public interface User extends UserDoc.Complete {
}
