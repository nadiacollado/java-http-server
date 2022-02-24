package httpserver.request;

import httpserver.interfaces.IReader;
import httpserver.interfaces.IRequestParser;
import httpserver.models.Request;

import java.io.*;

import static httpserver.utils.Constants.*;

public class RequestParser implements IRequestParser {
    public IReader reader;
    private String contentLength;
    private RequestBuilder requestBuilder;

    public RequestParser (RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public Request parse(IReader requestReader) throws IOException {
        reader = requestReader;
        String startLine = reader.readLine();

        if (startLine == null) {
           return null;
        }

        setStartLine(startLine);
        setHeaders();
        setBody(contentLength);
        return requestBuilder.build();
    }

    public void setStartLine(String startLine) {
        String[] requestElements = startLine.split(" ");
        requestBuilder
                .setMethod(requestElements[0])
                .setPath(requestElements[1])
                .setProtocol(requestElements[2]);
    }

    public void setHeaders() throws IOException {
        String headerLine;
        while ((headerLine = reader.readLine()) != null && headerLine.length() > 0) {
            if (isEmptyLine(headerLine)){
                break;
            } else {
                String[] headerElements = headerLine.split(": ");
                if (headerElements[0].equals("Content-Length")) {
                    contentLength = headerElements[1];
                }
                requestBuilder.addHeader(headerElements[0], headerElements[1]);
            }
        }
    }

    public void setBody(String contentLength) throws IOException {
        if (contentLength != null && !contentLength.equals("0")) {
            String body = null;
            int bodyLength = Integer.parseInt(contentLength);
            char[] container = new char[bodyLength];
            reader.read(container, 0, bodyLength);
            body = new String(container, 0, bodyLength);

            requestBuilder.setBody(body);
        }
    }

    public boolean isEmptyLine(String line) {
        return line.equals("") || line.equals(DOUBLE_LINE_BREAK);
    }
}
