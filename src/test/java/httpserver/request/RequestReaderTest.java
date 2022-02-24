package httpserver.request;

import httpserver.mocks.MockBufferedReader;
import httpserver.mocks.MockReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RequestReaderTest {
    @Test
    public void returnsStringOfGivenInputLine() throws IOException {
        String startLine = "GET /simple_get HTTP/1.1";
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        mockBufferedReader.setReadLineValues(new String[] {"GET /simple_get HTTP/1.1", "Content-Type: text/plain"});
        RequestReader requestReader = new RequestReader(mockBufferedReader);
        String readerLine = requestReader.readLine();

        assertTrue(mockBufferedReader.wasReadLineCalled());
        assertEquals(startLine, readerLine);
    }

    @Test
    public void returnsCharacterArrayOfGivenInputLine() throws IOException {
        String body = "Body Line";
        char [] container = new char[body.length()];
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        mockBufferedReader.setReadValues(body);
        RequestReader requestReader = new RequestReader(mockBufferedReader);
        requestReader.read(container, 0, body.length());

        assertTrue(mockBufferedReader.wasReadCalled());
    }

    @Test
    public void closesTheReader() throws IOException {
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        RequestReader requestReader = new RequestReader(mockBufferedReader);

        requestReader.close();

        assertTrue(mockBufferedReader.wasCloseCalled());
    }
}