package net.astrocube.commons.bukkit.board;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.astrocube.api.bukkit.board.Board;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Packet-based implementation of {@link Board},
 * this implementation uses the Bukkit API to create
 * the scoreboard objects, but directly uses packets
 * to update the scoreboard
 */
public class PacketBoard implements Board {

	/**
	 * Content of the scoreboard, contains all the entries
	 */
	private final Int2ObjectMap<String> lines
		= new Int2ObjectOpenHashMap<>();

	/**
	 * Reference to the spigot scoreboard object
	 */
	private final Scoreboard scoreboard;

	/**
	 * The linked player, the viewer of this board
	 */
	private final Player viewer;

	/**
	 * Determines if the scoreboard has been deleted
	 */
	private boolean deleted;

	/**
	 * The title of the scoreboard
	 */
	private String title;

	/**
	 * The buffer objective (hidden)
	 */
	private Objective buffer;

	/**
	 * The shown objective
	 */
	private Objective objective;

	public PacketBoard(
		Scoreboard scoreboard,
		Player viewer,
		Objective objective,
		Objective buffer
	) {
		this.scoreboard = scoreboard;
		this.viewer = viewer;
		this.objective = objective;
		this.buffer = buffer;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		checkNotDeleted();
		if (this.title.equals(title)) {
			// don't do anything if title is equal
			return;
		}
		this.title = title;
		objective.setDisplayName(title);
		sendObjective(objective, ObjectiveUpdateMode.UPDATE);
	}

	@Override
	public String getLine(int index) {
		checkNotDeleted();
		return lines.get(index);
	}

	@Override
	public void set(int index, String line) {
		checkNotDeleted();
		String old = lines.get(index);

		// same text, don't update
		if (line.equals(old)) {
			return;
		}

		if (old != null) {
			// remove the old score
			removeBufferScore(old, index);
			// send the new score
			updateBufferScore(line, index);
			// swap buffer <-> objective
			swapObjectives();
			// remove the old score from the old objective
			removeBufferScore(old, index);
		} else {
			// there's no an old score
			// just add a score to the objective
			addScore(line, index);
		}

		updateBufferScore(line, index);
		lines.put(index, line);
	}

	@Override
	public void setLines(List<String> lines) {

		// remove extras
		ObjectIterator<Int2ObjectMap.Entry<String>> iterator
			= this.lines.int2ObjectEntrySet().iterator();
		while (iterator.hasNext()) {
			Int2ObjectMap.Entry<String> entry = iterator.next();
			if (entry.getIntKey() > lines.size()) {
				scoreboard.resetScores(entry.getValue());
				iterator.remove();
			}
		}

		// update
		for (int i = 0; i < lines.size(); i++) {
			int score = lines.size() - i - 1;
			set(score, lines.get(i));
		}
	}

	@Override
	public Int2ObjectMap<String> getLines() {
		return new Int2ObjectOpenHashMap<>(lines);
	}

	@Override
	public void remove(int index) {
		checkNotDeleted();
		String text = lines.get(index);
		if (text != null) {
			scoreboard.resetScores(text);
			lines.remove(index);
		}
	}

	@Override
	public void delete() {
		checkNotDeleted();
		sendObjective(objective, ObjectiveUpdateMode.REMOVE);
		sendObjective(buffer, ObjectiveUpdateMode.REMOVE);
		objective.unregister();
		buffer.unregister();
		deleted = true;
	}

	@Override
	public void clear() {
		for (int score : lines.keySet()) {
			remove(score);
		}
		lines.clear();
	}

	/**
	 * Swaps the objectives, the objectives
	 * should be swapped when the modifications
	 * executed to the buffer objective are
	 * ready.
	 */
	private void swapObjectives() {
		sendObjectiveDisplaySet(buffer);
		Objective tmp = buffer;
		buffer = objective;
		objective = tmp;
	}

	private void sendObjectiveDisplaySet(Objective objective) {
		// CraftObjective is exposed by GammaSpigot, use it!
		ScoreboardObjective handle = ((CraftObjective) objective).getHandle();
		((CraftPlayer) viewer).getHandle()
			.b
			.sendPacket(new PacketPlayOutScoreboardDisplayObjective(
				1,
				handle
			));
	}

	private void sendScoreUpdate(
		Objective objective,
		String name,
		int index,
		boolean remove
	) {
		net.minecraft.world.scores.Scoreboard handle =
			((CraftScoreboard) scoreboard).getHandle();
		ScoreboardObjective objectiveHandle = ((CraftObjective) objective)
			.getHandle();

		ScoreboardScore score = new ScoreboardScore(handle, objectiveHandle, name);
		score.setScore(index);


		Map<String, Map<ScoreboardObjective, ScoreboardScore>> scores =
			handle.getPlayerScoresMap();

		Packet<?> packet;

		if (remove) {
			Map<ScoreboardObjective, ScoreboardScore> scoresByObjective = scores.get(name);
			if (scoresByObjective != null) {
				scoresByObjective.remove(objectiveHandle);
			}
			packet = new PacketPlayOutScoreboardScore(name, objectiveHandle);
		} else {
			scores.computeIfAbsent(name, n -> new HashMap<>())
				.put(objectiveHandle, score);

			packet = new PacketPlayOutScoreboardScore(score);
		}

		((CraftPlayer) viewer).getHandle()
			.b
			.sendPacket(packet);
	}

	private void addScore(String line, int score) {
		sendScoreUpdate(objective, line, score, false);
	}

	private void removeBufferScore(String line, int score) {
		sendScoreUpdate(buffer, line, score, true);
	}

	private void updateBufferScore(String line, int score) {
		sendScoreUpdate(buffer, line, score, false);
	}

	private void sendObjective(Objective objective, ObjectiveUpdateMode mode) {
		ScoreboardObjective handle = ((CraftObjective) objective).getHandle();
		((CraftPlayer) viewer).getHandle().b
			.sendPacket(new PacketPlayOutScoreboardObjective(
				handle,
				mode.ordinal()
			));
	}

	private void checkNotDeleted() {
		if (deleted) {
			throw new IllegalStateException("You can't use a scoreboard"
				+ " that has been deleted!");
		}
	}

	/**
	 * Represents a update mode,
	 * order is important, {@link ObjectiveUpdateMode#ordinal()}
	 * is used to send the packet
	 */
	enum ObjectiveUpdateMode {
		CREATE,
		REMOVE,
		UPDATE
	}

	/**
	 * Checks the player scoreboard, if its scoreboard
	 * doesn't exist, or it's the server main scoreboard,
	 * creates a new scoreboard and is returned. Else, the
	 * current player scoreboard is returned.
	 */
	private static Scoreboard getScoreboard(Player player) {
		ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
		Scoreboard mainScoreboard = scoreboardManager.getMainScoreboard();
		Scoreboard scoreboard = player.getScoreboard();

		if (scoreboard == null || scoreboard == mainScoreboard) {
			return scoreboardManager.getNewScoreboard();
		} else {
			return player.getScoreboard();
		}
	}

	/**
	 * Gets the objective registered with
	 * the specified {@code name} from the
	 * given {@code scoreboard}, if it doesn't
	 * exist, the method registers it.
	 */
	private static Objective getObjective(
		Scoreboard scoreboard,
		String name
	) {
		Objective objective = scoreboard.getObjective(name);
		if (objective == null) {
			return scoreboard.registerNewObjective(name, "dummy");
		} else {
			return objective;
		}
	}

	public static Board create(
		Player player,
		String title
	) {
		// get the player scoreboard or create a new one
		Scoreboard scoreboard = getScoreboard(player);

		// use the player name to create the objectives
		String subName = player.getName().length() <= 14
			? player.getName()
			: player.getName().substring(0, 14);

		// get or register the objectives
		Objective objective = getObjective(scoreboard, "m-" + subName);
		Objective buffer = getObjective(scoreboard, "b-" + subName);

		PacketBoard board = new PacketBoard(scoreboard, player, objective, buffer);

		// set the title in the shown objective
		objective.setDisplayName(title);

		board.sendObjective(objective, ObjectiveUpdateMode.CREATE);
		board.sendObjectiveDisplaySet(objective);

		// set the title in the buffer objective
		buffer.setDisplayName(title);

		board.sendObjective(buffer, ObjectiveUpdateMode.CREATE);

		// finally set the player scoreboard
		player.setScoreboard(scoreboard);

		return board;
	}

}
