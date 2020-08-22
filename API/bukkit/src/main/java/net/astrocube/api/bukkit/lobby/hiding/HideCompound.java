package net.astrocube.api.bukkit.lobby.hiding;

public interface HideCompound {

    /**
     * Check if compound will hide user friends when active.
     * @return boolean indicating property
     */
    boolean friends();

    /**
     * Check if compound will hide staff when active.
     * @return boolean indicating property
     */
    boolean staff();

    /**
     * Check if compound will hide players with "lobby.hide.bypass" permission.
     * @return boolean indicating property
     */
    boolean permission();

}
