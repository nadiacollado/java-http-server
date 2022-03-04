package httpserver.mocks;

import httpserver.interfaces.IReader;

public class MockRequestReader implements IReader {
    private boolean readLineWasCalled = false;
    private boolean readWasCalled = false;

    private MockBufferedReader reader;

    public MockRequestReader(MockBufferedReader reader) {
        this.reader = reader;
    }

    public String readLine() {
        readLineWasCalled = true;
        return reader.readLine();
    }

    public int read(char[] container, int offset, int length) {
        readWasCalled = true;
        return reader.read(container, offset, length);
    }

    public void close() {}

    public boolean wasReadLineCalled() {
        return readLineWasCalled;
    }

    public boolean wasReadCalled() {
        return readWasCalled;
    }
}
