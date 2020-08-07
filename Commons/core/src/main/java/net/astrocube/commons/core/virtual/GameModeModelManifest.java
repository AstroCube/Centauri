package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.utils.TypeArgument;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.commons.core.service.CoreModelService;

public class GameModeModelManifest extends ProtectedModule implements ModelBinderModule {

    @Override
    protected void configure() {
        bindModel(GameMode.class, GameMode.class, model -> {
            TypeLiteral<CoreModelService<GameMode, GameMode>> serviceTypeLiteral = new ResolvableType<CoreModelService<GameMode, GameMode>>(){}.with(
                    new TypeArgument<GameMode>(model.getCompleteTypeLiteral()){},
                    new TypeArgument<GameMode>(model.getPartialTypeLiteral()){}
            );
            model.bind(serviceTypeLiteral);
        });
    }

}
