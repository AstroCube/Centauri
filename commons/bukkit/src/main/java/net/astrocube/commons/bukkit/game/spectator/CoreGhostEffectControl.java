package net.astrocube.commons.bukkit.game.spectator;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardTeam;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

@Singleton
public class CoreGhostEffectControl implements GhostEffectControl {

	private static final String TEAM_NAME = "GHOST";

	private final Scoreboard scoreboard = new Scoreboard();
	private ScoreboardTeam scoreboardTeam;

	private final Set<String> players = new HashSet<>();

	@Override
	public void createTeam() {

		scoreboardTeam
			= new ScoreboardTeam(scoreboard, TEAM_NAME);

		scoreboardTeam.setCanSeeFriendlyInvisibles(true);

	}

	@Override
	public void addPlayer(Player player) {
		System.out.println("InternalGhostEffectControl");
		if (!players.contains(player.getName())) {
			players.add(player.getName());

			// TODO: Modify spigot so we don't have to do this trick
			Iterator<String> iterator = players.iterator();
			PacketPlayOutScoreboardTeam packet = PacketPlayOutScoreboardTeam.a(
				scoreboardTeam,
				iterator.next(),
				PacketPlayOutScoreboardTeam.a.a
			);

			while (iterator.hasNext()) {
				packet.e().add(iterator.next());
			}

			sendPacket(player, packet);
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));
		}
	}

	@Override
	public void removePlayer(Player player) {
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
	}

	@Override
	public boolean isGhost(Player player) {
		return players.contains(player.getName());
	}

	private void sendPacket(Player player, Packet<?> packet) {
		((CraftPlayer) player)
			.getHandle().b
			.sendPacket(packet);
	}

}
