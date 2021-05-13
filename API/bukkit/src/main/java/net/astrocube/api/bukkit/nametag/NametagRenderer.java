package net.astrocube.api.bukkit.nametag;

import org.bukkit.entity.Player;

public interface NametagRenderer<T extends Nametag, R extends Nametag.Rendered> {

	R render(T nametag, Player player);

}