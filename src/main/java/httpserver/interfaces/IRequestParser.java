package httpserver.interfaces;

import httpserver.request.RequestBuilder;
import httpserver.models.Request;

import java.io.IOException;
import java.io.InputStream;

public interface IRequestParser {
    Request parse(InputStream request) throws IOException;
    void setStartLine(String startLine, RequestBuilder requestBuilder);
    void setHeaders(RequestBuilder requestBuilder) throws IOException;
    void setBody(String contentLength, RequestBuilder requestBuilder) throws IOException;
}
