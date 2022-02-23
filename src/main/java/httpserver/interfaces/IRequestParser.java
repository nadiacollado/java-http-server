package httpserver.interfaces;

import httpserver.models.Request;
import httpserver.request.RequestReader;

import java.io.IOException;
import java.io.InputStream;

public interface IRequestParser {
    Request parse(IReader reader) throws IOException;
    void setStartLine(String startLine);
    void setHeaders() throws IOException;
    void setBody(String contentLength) throws IOException;
}
