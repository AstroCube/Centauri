package net.astrocube.api.bukkit.tablist;


import java.util.List;

public interface TablistCompound {

	/**
	 * @return list of strings containing tablist header
	 */
	List<String> getHeader();

	/**
	 * @return list of strings containing tablist footer
	 */
	List<String> getFooter();

}
