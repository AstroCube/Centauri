package net.astrocube.api.core.cloud;

public interface CloudStatusProvider {

    /**
     * @return if has cloud service hooked
     */
    boolean hasCloudHooked();

    /**
     * @return online number at the cloud
     */
    int getOnline();

    /**
     * @return if player is online
     */
    boolean hasAliveSession(String player);

    /**
     * @param player to be checked
     * @return server of the player
     */
    String getPlayerServer(String player);

}
