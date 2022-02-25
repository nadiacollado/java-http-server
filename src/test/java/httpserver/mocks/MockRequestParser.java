package httpserver.mocks;

import httpserver.interfaces.IReader;
import httpserver.interfaces.IRequestParser;
import httpserver.models.Request;
import httpserver.utils.Constants;
import httpserver.utils.Methods;

import static httpserver.utils.Constants.PROTOCOL;
import static httpserver.utils.Methods.GET;

public class MockRequestParser implements IRequestParser {
    public boolean parseCalled = false;
    public boolean setStartLineCalled = false;
    public boolean setHeadersCalled = false;
    public boolean setBodyCalled = false;

    @Override
    public Request parse(IReader reader) {
        parseCalled = true;
        return new Request(GET, "/simple_get", PROTOCOL, null, null);
    }

    @Override
    public void setStartLine(String startLine) {
        setStartLineCalled = true;
    }

    @Override
    public void setHeaders() {
        setHeadersCalled = true;
    }

    @Override
    public void setBody(String contentLength) {
        setBodyCalled = true;
    }

    public boolean wasParseCalled() {
        return parseCalled;
    }
}
