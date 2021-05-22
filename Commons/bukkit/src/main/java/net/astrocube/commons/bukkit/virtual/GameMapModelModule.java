package net.astrocube.commons.bukkit.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.map.GameMapDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.CoreModelService;

public class GameMapModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(GameMap.class, GameMapDoc.Partial.class)
			.toSingleService(new TypeLiteral<CoreModelService<GameMap, GameMapDoc.Partial>>() {});
	}

}