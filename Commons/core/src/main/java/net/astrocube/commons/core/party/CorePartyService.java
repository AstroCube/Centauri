package net.astrocube.commons.core.party;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.core.party.PartyService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.party.Party;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Common default implementation of {@link PartyService} */
public class CorePartyService implements PartyService {

    private @Inject QueryService<Party> partyQueryService;
    private @Inject ObjectMapper objectMapper;

    @Override
    public Optional<Party> getPartyOf(String userId) {

        ObjectNode asMemberQuery = objectMapper.createObjectNode();
        // members set also contain the leader
        asMemberQuery.putArray("members").add(userId);
        Set<Party> matches;

        try {
            // we can optimise it using a 'queryOneSync', but QueryService
            // doesn't allow it yet.
            matches = partyQueryService.querySync(asMemberQuery)
                    .getFoundModels();
        } catch (Exception e) {
            Logger.getGlobal()
                    .log(Level.WARNING, "[Commons] Failed to query party of user " + userId, e);
            return Optional.empty();
        }

        if (matches.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(matches.iterator().next());
        }
    }

}
