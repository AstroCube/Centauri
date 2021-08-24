package net.astrocube.commons.bukkit.nametag;

import net.astrocube.api.bukkit.nametag.Nametag;
import net.astrocube.api.bukkit.nametag.types.AbstractRenderedNametag;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.monster.EntitySlime;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public class SimpleRenderedNametag extends AbstractRenderedNametag {

	public SimpleRenderedNametag(Nametag nametag, Set<Entity> spawnedEntities, Player viewer) {
		super(nametag, spawnedEntities, viewer);
	}

	@Override
	public void setTag(String tag) {
		super.setTag(tag);

		Optional<Entity> standEntityOptional = this.getSpawnedEntities()
			.stream()
			.filter(entity -> entity.getIdentifier().equalsIgnoreCase("stand"))
			.findFirst();

		Entity standEntity = standEntityOptional.orElseThrow(() ->
			new IllegalArgumentException("Trying to interact with a nametag that does not have a stand entity")
		);

		EntityArmorStand armorStand = (EntityArmorStand) standEntity.getEntityObject();
		armorStand.setCustomNameVisible(!tag.isEmpty());
		armorStand.setCustomName(new ChatMessage(tag));

		DataWatcher dataWatcher = new DataWatcher(null);
		dataWatcher.register(new DataWatcherObject<>(2, DataWatcherRegistry.f), Optional.of(new ChatMessage(tag)));

		((CraftPlayer) this.getViewer()).getHandle().b.sendPacket(new PacketPlayOutEntityMetadata(
			armorStand.getId(),
			dataWatcher,
			true
		));
	}

	@Override
	public void hide() {
		PlayerConnection connection = ((CraftPlayer) this.viewer).getHandle().b;
		this.spawnedEntities.forEach(entity -> connection.sendPacket(new PacketPlayOutEntityDestroy(entity.getEntityId())));
	}

	@Override
	public void show() {
		Optional<Entity> slimeEntityOptional = this.getSpawnedEntities()
			.stream()
			.filter(entity -> entity.getIdentifier().equalsIgnoreCase("slime"))
			.findFirst();

		Entity slimeEntity = slimeEntityOptional.orElseThrow(() ->
			new IllegalArgumentException("Trying to interact with a nametag that does not have a slime entity")
		);

		Optional<Entity> standEntityOptional = this.getSpawnedEntities()
			.stream()
			.filter(entity -> entity.getIdentifier().equalsIgnoreCase("stand"))
			.findFirst();

		Entity standEntity = standEntityOptional.orElseThrow(() ->
			new IllegalArgumentException("Trying to interact with a nametag that does not have a stand entity")
		);

		Location location = this.getRecipient().getLocation();

		EntitySlime slime = (EntitySlime) slimeEntity.getEntityObject();
		slime.setPosition(
			location.getX(),
			location.getY(),
			location.getZ()
		);

		EntityArmorStand armorstand = (EntityArmorStand) standEntity.getEntityObject();
		armorstand.setPosition(
			location.getX(),
			location.getY(),
			location.getZ()
		);

		PlayerConnection playerConnection = ((CraftPlayer) this.viewer).getHandle().b;
		playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(slime));
		playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(armorstand));
		playerConnection.sendPacket(new PacketPlayOutAttachEntity(slime, ((CraftPlayer) this.getRecipient()).getHandle()));
		playerConnection.sendPacket(new PacketPlayOutAttachEntity(armorstand, slime));
	}
}