package httpserver.models;

import java.util.HashMap;

public class Request {
    public String method;
    public String path;
    public String protocol;
    public HashMap<String, String> headers;
    public String body;

    public Request() {}

    public Request(String method, String path, String protocol, HashMap<String, String> headers, String body) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.headers = headers;
        this.body = body;
    }
}
