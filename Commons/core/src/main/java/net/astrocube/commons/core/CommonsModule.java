package net.astrocube.commons.core;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.core.http.HttpModule;
import net.astrocube.commons.core.virtual.ModelManifest;

public class CommonsModule extends ProtectedModule {

    @Override
    protected void configure() {
        install(new HttpModule());
        install(new ModelManifest());
    }

}
