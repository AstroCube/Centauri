package net.astrocube.lobby.hide;

import net.astrocube.api.bukkit.lobby.hide.HideCompound;

public class HideCompoundCollection {

	public static HideCompound aloneCompound() {
		return createCompound(false, false, false);
	}

	public static HideCompound friendlessCompound() {
		return createCompound(false, true, true);
	}

	public static HideCompound defaultCompound() {
		return createCompound(true, true, true);
	}

	public static HideCompound createCompound(boolean friends, boolean staff, boolean permission) {
		return new HideCompound() {
			@Override
			public boolean friends() {
				return friends;
			}

			@Override
			public boolean staff() {
				return staff;
			}

			@Override
			public boolean permission() {
				return permission;
			}
		};
	}

}
