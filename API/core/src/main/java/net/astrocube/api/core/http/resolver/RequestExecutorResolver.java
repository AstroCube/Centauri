package net.astrocube.api.core.http.resolver;

import java.util.concurrent.ExecutorService;

public interface RequestExecutorResolver {

    /**
     * Will resolve between a cached thread pool or concurrent defined pool
     * @return resolved executor
     */
    ExecutorService getExecutor();

}
