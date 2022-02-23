package httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MappersTest {
    @Test
    public void returnsJSONStringOfExampleObject() throws JsonProcessingException {
        Mappers mapper = new Mappers();
        String convertedObj = mapper.convertObjToJSON("cat", "dog");

        assertEquals("{\"key1\":\"cat\",\"key2\":\"dog\"}", convertedObj);
    }

    @Test
    public void returnsXMLStringOfNoteObject() throws IOException {
        Mappers mappers = new Mappers();
        String convertedObj = mappers.convertObjToXML("TEST Response");

        assertEquals("<note><body>TEST Response</body></note>", convertedObj);
    }
}