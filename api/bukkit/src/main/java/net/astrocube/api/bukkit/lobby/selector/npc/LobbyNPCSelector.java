package net.astrocube.api.bukkit.lobby.selector.npc;

import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.GameMode;

import java.beans.ConstructorProperties;

public class LobbyNPCSelector {

	private final String mode;
	private final String subMode;
	private final String value;
	private final String signature;
	private final double x;
	private final double y;
	private final double z;
	private final int yaw;
	private final int pitch;

	@ConstructorProperties({
			"mode", "subMode", "value", "signature", "x", "y", "z", "yaw", "pitch"
	})
	public LobbyNPCSelector(
			String mode,
			String subMode,
			String value,
			String signature,
			double x, double y, double z,
			int yaw, int pitch
	) {
		this.mode = mode;
		this.subMode = subMode;
		this.value = value;
		this.signature = signature;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	/**
	 * @return {@link GameMode} id of the selector
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @return {@link SubGameMode} id of the selector
	 */
	public String getSubMode() {
		return subMode;
	}

	/**
	 * @return skin value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return skin signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @return X coordinate to spawn
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return Y coordinate to spawn
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return Z coordinate to spawn
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @return Yaw coordinate to spawn
	 */
	public int getYaw() {
		return yaw;
	}

	/**
	 * @return Pitch coordinate to spawn
	 */
	public int getPitch() {
		return pitch;
	}

}
