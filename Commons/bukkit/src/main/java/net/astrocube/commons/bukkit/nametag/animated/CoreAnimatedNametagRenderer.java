package net.astrocube.commons.bukkit.nametag.animated;

import net.astrocube.api.bukkit.nametag.Nametag;
import net.astrocube.api.bukkit.nametag.types.animated.AnimatedNametag;
import net.astrocube.api.bukkit.nametag.types.animated.AnimatedNametagRenderer;
import net.astrocube.api.bukkit.nametag.types.animated.RenderedAnimatedNametag;
import net.astrocube.commons.bukkit.nametag.RenderUtil;
import org.bukkit.entity.Player;

import java.util.Set;

public class CoreAnimatedNametagRenderer implements AnimatedNametagRenderer {

	@Override
	public RenderedAnimatedNametag render(AnimatedNametag nametag, Player player) {
		Set<Nametag.Rendered.Entity> spawnedEntities = RenderUtil.getEntities(nametag);
		RenderedAnimatedNametag animatedNametag = new CoreRenderedAnimatedNametag(nametag, spawnedEntities, player);
		animatedNametag.show();

		return animatedNametag;
	}
}