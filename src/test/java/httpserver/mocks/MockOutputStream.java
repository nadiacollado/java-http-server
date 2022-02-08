package httpserver.mocks;

import java.io.OutputStream;

public class MockOutputStream extends OutputStream {
    private boolean writeCalled = false;

    @Override
    public void write(int b) {
        writeCalled = true;
    }

    public boolean wasWriteCalled() {
        return writeCalled;
    }
}
