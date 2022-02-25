package httpserver.mocks;

import java.io.Reader;

public class MockReader extends Reader {

    @Override
    public int read(char[] container, int off, int len) { return 0; }

    @Override
    public void close() {}
}
