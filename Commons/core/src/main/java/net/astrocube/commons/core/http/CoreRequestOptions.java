package net.astrocube.commons.core.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.astrocube.api.core.http.RequestOptions;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CoreRequestOptions implements RequestOptions {

    private final @NonNull Type type;
    private final Map<String, String> headers = new HashMap<>();
    private final @NonNull String body;
    private final String query = "";

}
