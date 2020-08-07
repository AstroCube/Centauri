package net.astrocube.commons.core.virtual;

import com.google.inject.*;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.utils.TypeArgument;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.commons.core.service.CoreModelService;

public class ServerModelManifest extends ProtectedModule implements ModelBinderModule {

    @Override
    protected void configure() {
        bindModel(Server.class, ServerDoc.Complete.class, model -> {
            TypeLiteral<CoreModelService<Server, ServerDoc.Complete>> serviceTypeLiteral = new ResolvableType<CoreModelService<Server, ServerDoc.Complete>>(){}.with(
                    new TypeArgument<Server>(model.getCompleteTypeLiteral()){},
                    new TypeArgument<ServerDoc.Complete>(model.getPartialTypeLiteral()){}
            );
            model.bind(serviceTypeLiteral);
        });
    }

}
