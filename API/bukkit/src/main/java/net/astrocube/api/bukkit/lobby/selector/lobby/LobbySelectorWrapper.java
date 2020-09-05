package net.astrocube.api.bukkit.lobby.selector.lobby;

public interface LobbySelectorWrapper {

    /**
     * Obtain name of the wrapper
     * @return string containing name
     */
    String getName();

    /**
     * Obtain actual connected users
     * @return indicator of connected users
     */
    int getConnected();

    /**
     * Obtain max allowed users at a certain lobby
     * @return indicator of max users
     */
    int getMax();

    /**
     * Check if lobby is full
     * @return indicator of connected users
     */
    boolean isFull();

    /**
     * Obtain lobby number
     * @return lobby number to be shown
     */
    int getNumber();

}
