package net.astrocube.commons.core.http.request;

import com.google.api.client.http.GenericUrl;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilderUtil {

    public static GenericUrl build(String baseUrl, String path, @Nullable String query) {
        try {
            URL url = new URL(baseUrl);
            return query == null ? new GenericUrl(new URL(url, path))
                    : new GenericUrl(new URL(url, path + query));
        } catch(MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
