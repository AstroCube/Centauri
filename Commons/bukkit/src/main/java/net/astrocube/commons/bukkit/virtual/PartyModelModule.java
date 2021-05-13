package net.astrocube.commons.bukkit.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.api.core.virtual.party.PartyDoc;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.DirectRedisModelService;

public class PartyModelModule extends ProtectedModule implements ModelBinderModule {

	@Override
	protected void configure() {
		bindModel(Party.class, PartyDoc.Partial.class, model -> {
			TypeLiteral<DirectRedisModelService<Party, PartyDoc.Partial>> serviceTypeLiteral =
				new ResolvableType<DirectRedisModelService<Party, PartyDoc.Partial>>() {
				};
			model.bind(serviceTypeLiteral);
		});
	}

}
