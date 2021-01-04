package net.astrocube.api.core.party;

import net.astrocube.api.core.virtual.party.Party;

import java.util.Optional;

/**
 * Service of {@link Party} handle
 * that contains a set of util methods
 * for handling the parties
 */
public interface PartyService {

    /**
     * Fetches the party of the given {@code userId}
     *
     * @param userId The member or leader of the
     *               searched party
     * @return The found party, empty if user isn't member
     * or leader of a party
     */
    Optional<Party> getPartyOf(String userId);

}
