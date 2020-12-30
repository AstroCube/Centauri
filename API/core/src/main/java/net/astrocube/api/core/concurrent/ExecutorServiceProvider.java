package net.astrocube.api.core.concurrent;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceProvider {

    /**
     * Executor service to be used for any asynchronous implementations
     * @return executor service
     */
    ExecutorService getRegisteredService();

    /**
     * Will provide the number of configured threads from any implementation
     * @return number of configured threads
     */
    int getConfiguredThreads();

}
