package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.CoreModelService;

public class PunishmentsModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(Punishment.class, PunishmentDoc.Partial.class)
			.toSingleService(new TypeLiteral<CoreModelService<Punishment, PunishmentDoc.Partial>>() {});
	}

}