package net.astrocube.commons.core.http.request;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.util.Charsets;

import java.io.IOException;
import java.io.OutputStream;

public class RequestContent extends AbstractHttpContent {

    final String json;

    public RequestContent(String json) {
        super("application/json");
        this.json = json;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        out.write(json.getBytes(Charsets.UTF_8));
    }

}
