package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.virtual.perk.StorablePerk;
import net.astrocube.api.core.virtual.perk.StorablePerkDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.CoreModelService;

public class PerkModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(StorablePerk.class, StorablePerkDoc.Partial.class)
			.toSingleService(new TypeLiteral<CoreModelService<StorablePerk, StorablePerkDoc.Partial>>() {});
	}

}
