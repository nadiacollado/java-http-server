package httpserver.mocks;

import java.io.Reader;

public class MockReader extends Reader {

    @Override
    public int read(char[] cbuf, int off, int len) {
        return 0;
    }

    @Override
    public void close() {}
}
