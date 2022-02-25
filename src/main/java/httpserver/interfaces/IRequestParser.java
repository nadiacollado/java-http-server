package httpserver.interfaces;

import httpserver.models.Request;

import java.io.IOException;

public interface IRequestParser {
    Request parse(IReader reader) throws IOException;
    void setStartLine(String startLine);
    void setHeaders() throws IOException;
    void setBody(String contentLength) throws IOException;
}
