package httpserver.request;

import httpserver.models.Request;

import java.util.HashMap;

public class RequestBuilder {
    private String method;
    private String path;
    private String protocol;
    private HashMap<String, String> headers;
    private String body;

    public RequestBuilder() {
        headers = new HashMap<>();
    }

    public Request build() {
        return new Request(method, path, protocol, headers, body);
    }

    public RequestBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public RequestBuilder addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public RequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }
}
