package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.CoreModelService;

public class ServerModelModule extends ProtectedModule implements ModelBinderModule {

	@Override
	protected void configure() {
		bindModel(Server.class, ServerDoc.Partial.class, model -> {
			TypeLiteral<CoreModelService<Server, ServerDoc.Partial>> serviceTypeLiteral =
				new ResolvableType<CoreModelService<Server, ServerDoc.Partial>>() {};
			model.bind(serviceTypeLiteral);
		});
	}

}
