package net.astrocube.commons.bukkit.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.api.core.virtual.party.PartyDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.DirectRedisModelService;

public class PartyModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(Party.class, PartyDoc.Partial.class)
			.toSingleService(new TypeLiteral<DirectRedisModelService<Party, PartyDoc.Partial>>() {});
	}

}
