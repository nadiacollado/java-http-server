package httpserver.mocks;

import httpserver.interfaces.IReader;

import java.io.IOException;

public class MockRequestReader implements IReader {
    @Override
    public String readLine() throws IOException {
        return null;
    }

    @Override
    public void read(char[] body, int offset, int length) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
