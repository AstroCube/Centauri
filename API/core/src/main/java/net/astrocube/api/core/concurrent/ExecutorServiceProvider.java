package net.astrocube.api.core.concurrent;

import com.google.common.util.concurrent.ListeningExecutorService;

public interface ExecutorServiceProvider {

    /**
     * Executor service to be used for any asynchronous implementations
     * @return executor service
     */
    ListeningExecutorService getRegisteredService();

    /**
     * Will provide the number of configured threads from any implementation
     * @return number of configured threads
     */
    int getConfiguredThreads();

}
