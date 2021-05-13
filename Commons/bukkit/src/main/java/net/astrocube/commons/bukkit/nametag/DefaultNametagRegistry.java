package net.astrocube.commons.bukkit.nametag;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.astrocube.api.bukkit.nametag.Nametag;
import net.astrocube.api.bukkit.nametag.NametagRegistry;
import net.astrocube.api.core.utils.MultimapCollector;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class DefaultNametagRegistry implements NametagRegistry {

	private final Multimap<String, Nametag.Rendered> renderedCharacters = MultimapBuilder.hashKeys().hashSetValues().build();

	@Override
	public Multimap<String, Nametag.Rendered> getRenderedNametags() {
		return this.renderedCharacters;
	}

	@Override
	public <T extends Nametag.Rendered> Multimap<String, T> getRenderedNametags(Class<T> type) {
		return this.renderedCharacters
			.values()
			.stream()
			.filter(entry -> type.isAssignableFrom(entry.getClass()))
			.map(o -> (T) o)
			.collect(MultimapCollector.toHashMultiMap(t -> t.getRecipient().getDatabaseIdentifier(), Function.identity()));
	}

	@Override
	public Multimap<String, Nametag.Rendered> getRenderedForPlayer(Player player) {
		return this.renderedCharacters
			.values()
			.stream()
			.filter(entry -> entry.getViewer().getDatabaseIdentifier().equals(player.getDatabaseIdentifier()))
			.collect(MultimapCollector.toHashMultiMap(rendered -> rendered.getRecipient().getDatabaseIdentifier(), Function.identity()));
	}

	@Override
	public <T extends Nametag.Rendered> Multimap<String, T> getRenderedForPlayer(Player player, Class<T> type) {
		return this.renderedCharacters
			.values()
			.stream()
			.filter(entry -> type.isAssignableFrom(entry.getClass()) &&
				entry.getViewer().getDatabaseIdentifier().equals(player.getDatabaseIdentifier()))
			.map(o -> (T) o)
			.collect(MultimapCollector.toHashMultiMap(t -> t.getRecipient().getDatabaseIdentifier(), Function.identity()));
	}

	@Override
	public void submit(Nametag.Rendered rendered) {
		this.renderedCharacters.put(rendered.getRecipient().getDatabaseIdentifier(), rendered);
	}

	@Override
	public void delete(String recipient) {
		if (this.renderedCharacters.containsKey(recipient)) {
			this.renderedCharacters.get(recipient).forEach(Nametag.Rendered::hide);
		}
		this.renderedCharacters.removeAll(recipient);
	}
}