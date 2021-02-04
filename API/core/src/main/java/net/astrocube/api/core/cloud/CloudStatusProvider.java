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

}
