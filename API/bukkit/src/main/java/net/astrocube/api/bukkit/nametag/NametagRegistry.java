package net.astrocube.api.bukkit.nametag;

import com.google.common.collect.Multimap;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface NametagRegistry {

    Multimap<String, Nametag.Rendered> getRenderedNametags();

    <T extends Nametag.Rendered> Multimap<String, T> getRenderedNametags(Class<T> type);

    Multimap<String, Nametag.Rendered> getRenderedForPlayer(Player player);

    <T extends Nametag.Rendered> Multimap<String, T> getRenderedForPlayer(Player player, Class<T> type);

    void submit(Nametag.Rendered rendered);

    void delete(String recipient);

}