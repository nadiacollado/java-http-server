package httpserver.mocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockInputStream extends InputStream {
    @Override
    public int read() throws IOException {
        return 0;
    }
}

