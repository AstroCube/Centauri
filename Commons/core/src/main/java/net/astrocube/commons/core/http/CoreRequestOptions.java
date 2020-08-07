package net.astrocube.commons.core.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.astrocube.api.core.http.RequestOptions;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CoreRequestOptions implements RequestOptions {

    private final @NonNull Type type;
    private final Map<String, String> headers;
    private final @NonNull String body;
    private final String query;

    public CoreRequestOptions(@NonNull Type type, Map<String, String> headers, @NonNull String body, String query) {
        this.type = type;
        this.headers = headers;
        this.body = body;
        this.query = query;
    }

    public CoreRequestOptions(@NonNull Type type, @NonNull String body) {
        this.type = type;
        this.headers = new HashMap<>();
        this.body = body;
        this.query = "";
    }

}
