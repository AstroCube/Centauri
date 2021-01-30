package net.astrocube.api.core.concurrent;

import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class SimpleAsyncResponse<T> implements AsyncResponse<T> {

    private final CompletableFuture<Response<T>> responseCompletableFuture;

    @Override
    public void callback(Callback<Response<T>> callback) {
        responseCompletableFuture.whenComplete((response, error) -> {
            if (error == null) {
                callback.call(response);
            } else {
                callback.handleException(error);
            }
        });
    }
}