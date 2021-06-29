package net.astrocube.api.bukkit.user.inventory.nbt;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtils {

	private static NBTTagCompound getTag(org.bukkit.inventory.ItemStack item) {
		net.minecraft.server.v1_8_R3.ItemStack itemNms = CraftItemStack.asNMSCopy(item);

		return itemNms.hasTag() ? itemNms.getTag() : new NBTTagCompound();
	}

	private static ItemStack setTag(ItemStack item, NBTTagCompound tag) {
		net.minecraft.server.v1_8_R3.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
		itemNms.setTag(tag);

		return CraftItemStack.asBukkitCopy(itemNms);
	}

	public static org.bukkit.inventory.ItemStack addString(ItemStack item, String key, String value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setString(key, value);
		}

		return setTag(item, tag);
	}

	public static boolean hasString(ItemStack item, String key) {
		NBTTagCompound tag = getTag(item);

		return tag.hasKey(key);
	}

	public static String getString(ItemStack item, String key) {
		NBTTagCompound tag = getTag(item);

		return tag.getString(key);
	}

	public static ItemStack addInt(ItemStack item, String key, int value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setInt(key, value);
		}

		return setTag(item, tag);
	}

	public static ItemStack addDouble(ItemStack item, String key, double value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setDouble(key, value);
		}

		return setTag(item, tag);
	}

	public static ItemStack addFloat(ItemStack item, String key, float value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setFloat(key, value);
		}

		return setTag(item, tag);
	}

	public static ItemStack addLong(ItemStack item, String key, long value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setLong(key, value);
		}

		return setTag(item, tag);
	}

	public static ItemStack addBoolean(ItemStack item, String key, boolean value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setBoolean(key, value);
		}

		return setTag(item, tag);
	}
	public static ItemStack addByte(ItemStack item, String key, byte value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setByte(key, value);
		}

		return setTag(item, tag);
	}

	public static ItemStack addShort(ItemStack item, String key, short value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setShort(key, value);
		}

		return setTag(item, tag);
	}

	public static ItemStack addIntArray(ItemStack item, String key, int[] value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setIntArray(key, value);
		}

		return setTag(item, tag);
	}

	public static ItemStack addByteArray(ItemStack item, String key, byte[] value) {
		NBTTagCompound tag = getTag(item);

		if (!tag.hasKey(key)) {
			tag.setByteArray(key, value);
		}

		return setTag(item, tag);
	}

}
