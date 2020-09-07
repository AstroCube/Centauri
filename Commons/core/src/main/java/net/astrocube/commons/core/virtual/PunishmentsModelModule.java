package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.CoreModelService;

public class PunishmentsModelModule extends ProtectedModule implements ModelBinderModule {

    @Override
    protected void configure() {
        bindModel(Punishment.class, PunishmentDoc.Partial.class, model -> {
            TypeLiteral<CoreModelService<Punishment, PunishmentDoc.Partial>> punishmentTypeLiteral =
                    new ResolvableType<CoreModelService<Punishment, PunishmentDoc.Partial>>(){};
            model.bind(punishmentTypeLiteral);
        });
    }

}