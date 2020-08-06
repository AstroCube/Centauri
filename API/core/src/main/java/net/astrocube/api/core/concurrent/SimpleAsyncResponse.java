package net.astrocube.api.core.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;

@AllArgsConstructor
public class SimpleAsyncResponse<T> implements AsyncResponse<T> {

    private final ListenableFuture<Response<T>> responseFuture;
    private final ExecutorService executorService;

    @Override
    public void callback(Callback<Response<T>> callback) {
        Futures.addCallback(this.responseFuture, this.wrapCallback(callback), this.executorService);
    }

    private FutureCallback<Response<T>> wrapCallback(Callback<Response<T>> callback) {
        return new FutureCallback<Response<T>>() {

            @Override
            public void onSuccess(Response<T> response) {
                callback.call(response);
            }

            @Override
            public void onFailure(@Nonnull Throwable throwable) {
                callback.handleException(throwable);
            }
        };
    }
}