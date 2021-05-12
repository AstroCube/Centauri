package net.astrocube.api.core.virtual.friend;

import net.astrocube.api.core.model.ModelProperties;

@ModelProperties.RouteKey("friend")
@ModelProperties.Cache
public interface Friendship extends FriendshipDoc.Complete {
}