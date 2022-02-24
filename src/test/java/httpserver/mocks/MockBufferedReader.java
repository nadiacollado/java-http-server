package httpserver.mocks;

import java.io.BufferedReader;
import java.io.Reader;

public class MockBufferedReader extends BufferedReader {
    private String text;
    private boolean closeCalled = false;

    public MockBufferedReader(Reader in) {
        super(in);
    }

    public String readLine() {
        return "";
    }

    public int read(char[] body, int offset, int length) {
        text = new String(body).substring(offset, length);
        return 0;
    }

    public void close() {
        closeCalled = true;
    }

    public boolean wasCloseCalled() {
        return closeCalled;
    }
}
