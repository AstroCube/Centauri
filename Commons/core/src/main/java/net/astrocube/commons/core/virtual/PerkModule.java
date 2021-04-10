package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.perk.StorablePerk;
import net.astrocube.api.core.virtual.perk.StorablePerkDoc;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.CoreModelService;

public class PerkModule extends ProtectedModule implements ModelBinderModule {

    @Override
    protected void configure() {
        bindModel(StorablePerk.class, StorablePerkDoc.Partial.class, model -> {
            TypeLiteral<CoreModelService<StorablePerk, StorablePerkDoc.Partial>> serviceTypeLiteral =
                    new ResolvableType<CoreModelService<StorablePerk, StorablePerkDoc.Partial>>(){};
            model.bind(serviceTypeLiteral);
        });
    }

}
