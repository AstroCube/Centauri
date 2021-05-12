package net.astrocube.commons.bukkit.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.RedisModelService;

public class MatchModelModule extends ProtectedModule implements ModelBinderModule {

	@Override
	protected void configure() {
		bindModel(Match.class, MatchDoc.Partial.class, model -> {
			TypeLiteral<RedisModelService<Match, MatchDoc.Partial>> serviceTypeLiteral =
				new ResolvableType<RedisModelService<Match, MatchDoc.Partial>>() {
				};
			model.bind(serviceTypeLiteral);
		});
	}

}