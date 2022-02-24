package httpserver.request;

import httpserver.mocks.MockBufferedReader;
import httpserver.mocks.MockReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestReaderTest {

    @Test
    public void closesTheReader() throws IOException {
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        RequestReader requestReader = new RequestReader(mockBufferedReader);

        requestReader.close();

        assertTrue(mockBufferedReader.wasCloseCalled());
    }
}