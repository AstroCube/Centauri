package net.astrocube.api.core.virtual.party;

import net.astrocube.api.core.model.ModelProperties;

/**
 * Represents a players party.
 * Holds data like leader, members, etc.
 * @see PartyDoc
 */
@ModelProperties.RouteKey("party")
@ModelProperties.Cache
public interface Party extends PartyDoc.Complete {
}
