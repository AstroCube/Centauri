package net.astrocube.commons.bukkit.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.DirectRedisModelService;

public class MatchModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(Match.class, MatchDoc.Partial.class)
			.toSingleService(new TypeLiteral<DirectRedisModelService<Match, MatchDoc.Partial>>() {});
	}

}