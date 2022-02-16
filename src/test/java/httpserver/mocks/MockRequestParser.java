package httpserver.mocks;

import httpserver.request.RequestBuilder;
import httpserver.interfaces.IRequestParser;
import httpserver.models.Request;
import httpserver.utils.Methods;
import httpserver.utils.Constants;

import java.io.InputStream;

public class MockRequestParser implements IRequestParser {
    public boolean parseCalled = false;
    public boolean setStartLineCalled = false;
    public boolean setHeadersCalled = false;
    public boolean setBodyCalled = false;


    @Override
    public Request parse(InputStream requestStream) {
        parseCalled = true;
        return new Request(Methods.GET, "/simple_get", Constants.PROTOCOL, null, null);
    }

    @Override
    public void setStartLine(String startLine, RequestBuilder requestBuilder) {
        setStartLineCalled = true;
    }

    @Override
    public void setHeaders(RequestBuilder requestBuilder) {
        setHeadersCalled = true;
    }

    @Override
    public void setBody(String contentLength, RequestBuilder requestBuilder) {
        setBodyCalled = true;
    }

    public boolean wasParseCalled() {
        return parseCalled;
    }
}
