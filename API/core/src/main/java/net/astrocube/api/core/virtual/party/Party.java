package net.astrocube.api.core.virtual.party;

import net.astrocube.api.core.model.ModelProperties;
import net.astrocube.api.core.party.PartyService;

/**
 * Represents a players party.
 * Holds data like leader, members, etc.
 *
 * @see PartyService
 * @see PartyDoc
 */
@ModelProperties.RouteKey("party")
@ModelProperties.Cache
public interface Party extends PartyDoc.Complete {
}
