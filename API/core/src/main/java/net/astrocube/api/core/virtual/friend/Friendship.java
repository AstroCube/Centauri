package net.astrocube.api.core.virtual.friend;

import net.astrocube.api.core.model.ModelProperties;

@ModelProperties.RouteKey("friends")
@ModelProperties.Cache(0)
public interface Friendship extends FriendshipDoc.Complete {}