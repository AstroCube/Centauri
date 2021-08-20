package net.astrocube.api.bukkit.menu;

import com.google.common.collect.ImmutableSet;
import net.astrocube.api.bukkit.user.profile.AbstractGameProfile;
import net.astrocube.api.bukkit.user.profile.CoreGameProfile;
import net.astrocube.api.bukkit.user.profile.CoreProperty;
import net.astrocube.api.bukkit.user.profile.MojangProfileGeneratorUtil;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import team.unnamed.gui.core.gui.factory.GUIFactory;
import team.unnamed.gui.core.item.type.ItemBuilder;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.UUID;

public class MenuUtils {

	private static final Set<Integer> MARKED_SLOTS = ImmutableSet.of(
			9, 17, 18, 26, 27, 35, 36, 44
	);
	private static final Field PROFILE_FIELD;
	private static final boolean PROFILE_FIELD_ACCESSIBLE;

	static {
		try {
			Class<?> metaClass = Class.forName("org.bukkit.craftbukkit.v" + GUIFactory.SERVER_VERSION + ".inventory.CraftMetaSkull");

			PROFILE_FIELD = metaClass.getDeclaredField("profile");
			PROFILE_FIELD_ACCESSIBLE = PROFILE_FIELD.isAccessible();
		} catch (ClassNotFoundException | NoSuchFieldException e) {
			throw new IllegalStateException("Cannot get the SkullMeta profile field!", e);
		}
	}

	public static boolean isMarkedSlot(int slot) {
		return (slot >= 0 && slot < 9) ||
				(slot > 44 && slot < 54) ||
				MARKED_SLOTS.contains(slot);
	}

	public static ItemStack generateStainedPane() {
		return ItemBuilder.newDyeItemBuilder(
			Material.GRAY_STAINED_GLASS_PANE,
			DyeColor.GRAY
		)
			.setName(" ")
			.build();
	}

	public static ItemStack generateHead(HeadLibrary library) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta) head.getItemMeta();

		AbstractGameProfile profile = new CoreGameProfile(UUID.randomUUID(), "head");
		profile.getProperties().add(new CoreProperty("textures", library.getBase64()));

		PROFILE_FIELD.setAccessible(true);

		try {
			PROFILE_FIELD.set(meta, MojangProfileGeneratorUtil.generateProfile(profile));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			PROFILE_FIELD.setAccessible(PROFILE_FIELD_ACCESSIBLE);
		}

		head.setItemMeta(meta);
		return head;
	}

}