package httpserver.request;

import httpserver.interfaces.IRequestParser;
import httpserver.models.Request;

import java.io.*;

public class RequestParser implements IRequestParser {
   public BufferedReader reader;
   private String contentLength;

    public Request parse(InputStream httpRequest) throws IOException {
       reader = new BufferedReader(new InputStreamReader(httpRequest));
       RequestBuilder requestBuilder = new RequestBuilder();
       String startLine = reader.readLine();

       setStartLine(startLine, requestBuilder);
       setHeaders(requestBuilder);
       setBody(contentLength, requestBuilder);
       return requestBuilder.build();
    }

    public void setStartLine(String startLine, RequestBuilder requestBuilder) {
        String[] requestElements = startLine.split(" ");
        requestBuilder
            .setMethod(requestElements[0])
            .setPath(requestElements[1])
            .setProtocol(requestElements[2]);
    }

    public void setHeaders(RequestBuilder requestBuilder) throws IOException {
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

    public void setBody(String contentLength, RequestBuilder requestBuilder) throws IOException {
        if (contentLength != null && contentLength != "0") {
            String parsedBod = null;
            int bodyLength = Integer.parseInt(contentLength);
            char[] destination = new char[bodyLength];
            reader.read(destination, 0, bodyLength);
            parsedBod = new String(destination, 0, bodyLength);

            requestBuilder.setBody(parsedBod);
        }
    }

    public boolean isEmptyLine(String line) {
        return line.equals("");
    }
}
