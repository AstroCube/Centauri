package net.astrocube.api.core.concurrent;


public interface AsyncResponse<T> {

    void callback(Callback<Response<T>> callback);

}