package net.astrocube.commons.core;

import com.google.inject.Inject;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.commons.core.http.CoreRequestOptions;

public class CommonsModule {

    private @Inject
    HttpClient httpClient;

    public void idk() {
        this.httpClient.executeRequest("", String.class, new CoreRequestOptions("", ""));
    }
}
