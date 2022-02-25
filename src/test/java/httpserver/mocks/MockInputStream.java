package httpserver.mocks;

import java.io.InputStream;

public class MockInputStream extends InputStream {
    @Override
    public int read() {
        return 0;
    }
}

