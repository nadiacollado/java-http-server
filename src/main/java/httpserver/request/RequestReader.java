package httpserver.request;

import httpserver.interfaces.IReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestReader implements IReader {
    private BufferedReader reader;

    public RequestReader(BufferedReader reader) throws IOException {
        this.reader = reader;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public int read(char[] body, int offset, int length) throws IOException {
        return reader.read(body, offset, length);
    }

    public void close() throws IOException {
        reader.close();
    }
}

