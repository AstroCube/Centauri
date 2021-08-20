package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.GameModeDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.RedisModelService;

public class GameModeModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(GameMode.class, GameModeDoc.Partial.class)
			.toSingleService(new TypeLiteral<RedisModelService<GameMode, GameModeDoc.Partial>>() {});
	}

}
