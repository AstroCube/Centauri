package net.astrocube.api.core.http.resolver;

import com.google.common.util.concurrent.ListeningExecutorService;

public interface RequestExecutorResolver {

    /**
     * Will resolve between a cached thread pool or concurrent defined pool
     * @return resolved executor
     */
    ListeningExecutorService getExecutor();

}
