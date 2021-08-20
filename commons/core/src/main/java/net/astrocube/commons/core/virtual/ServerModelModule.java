package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.CoreModelService;

public class ServerModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(Server.class, ServerDoc.Partial.class)
			.toSingleService(new TypeLiteral<CoreModelService<Server, ServerDoc.Partial>>() {});
	}

}
