package httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.xml.XmlMapper;
import httpserver.utils.structured_data.ExampleObject;
import httpserver.utils.structured_data.note;

import java.io.IOException;

public class Mappers {
    public static String convertObjToJSON(String text1, String text2) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new ExampleObject(text1, text2));
    }

    public static String convertObjToXML(String text) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(new note(text));
    }
}
