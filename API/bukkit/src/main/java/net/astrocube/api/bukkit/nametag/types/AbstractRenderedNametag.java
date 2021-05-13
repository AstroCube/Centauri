package net.astrocube.api.bukkit.nametag.types;

import net.astrocube.api.bukkit.nametag.Nametag;
import org.bukkit.entity.Player;

import java.util.Set;

public abstract class AbstractRenderedNametag implements Nametag.Rendered {

	protected final Nametag nametag;
	protected String tag;
	protected final Set<Entity> spawnedEntities;
	protected final Player viewer;

	public AbstractRenderedNametag(Nametag nametag, Set<Entity> spawnedEntities, Player viewer) {
		this.nametag = nametag;
		this.tag = nametag.getTag();
		this.spawnedEntities = spawnedEntities;
		this.viewer = viewer;
	}

	@Override
	public Player getRecipient() {
		return this.nametag.getRecipient();
	}

	@Override
	public String getTag() {
		return this.tag;
	}

	@Override
	public String getTeamName() {
		return this.nametag.getTeamName();
	}

	@Override
	public Player getViewer() {
		return this.viewer;
	}

	@Override
	public Set<Entity> getSpawnedEntities() {
		return this.spawnedEntities;
	}

	@Override
	public void setTag(String tag) {
		this.tag = tag;
	}

	public static class SimpleEntity implements Entity {

		private final String identifier;
		private final int entityId;
		private final Object entityObject;

		public SimpleEntity(String identifier, int entityId, Object entityObject) {
			this.identifier = identifier;
			this.entityId = entityId;
			this.entityObject = entityObject;
		}

		@Override
		public String getIdentifier() {
			return this.identifier;
		}

		@Override
		public int getEntityId() {
			return this.entityId;
		}

		@Override
		public Object getEntityObject() {
			return this.entityObject;
		}
	}
}