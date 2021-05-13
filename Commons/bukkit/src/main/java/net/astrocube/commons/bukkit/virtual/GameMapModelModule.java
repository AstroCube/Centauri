package net.astrocube.commons.bukkit.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.map.GameMapDoc;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.CoreModelService;

public class GameMapModelModule extends ProtectedModule implements ModelBinderModule {

	@Override
	protected void configure() {
		bindModel(GameMap.class, GameMapDoc.Partial.class, model -> {
			TypeLiteral<CoreModelService<GameMap, GameMapDoc.Partial>> serviceTypeLiteral =
				new ResolvableType<CoreModelService<GameMap, GameMapDoc.Partial>>() {
				};
			model.bind(serviceTypeLiteral);
		});
	}

}