package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.gamemode.GameModeDoc;
import net.astrocube.commons.core.service.CoreModelService;

public class GameModeModelModule extends ProtectedModule implements ModelBinderModule {

    @Override
    protected void configure() {
        bindModel(GameMode.class, GameModeDoc.Partial.class, model -> {
            TypeLiteral<CoreModelService<GameMode, GameModeDoc.Partial>> serviceTypeLiteral =
                    new ResolvableType<CoreModelService<GameMode, GameModeDoc.Partial>>(){};
            model.bind(serviceTypeLiteral);
        });
    }

}
