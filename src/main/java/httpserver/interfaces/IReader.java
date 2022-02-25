package httpserver.interfaces;

import java.io.IOException;

public interface IReader {
    String readLine() throws IOException;
    int read(char[] body, int offset, int length) throws IOException;
    void close() throws IOException;
}
