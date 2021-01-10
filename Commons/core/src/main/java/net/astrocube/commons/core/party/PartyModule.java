package net.astrocube.commons.core.party;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.party.PartyService;

public class PartyModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(PartyService.class).to(CorePartyService.class);
        expose(PartyService.class);
    }
}
