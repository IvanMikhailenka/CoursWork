package parsers.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import parsers.xsd.StaxXsdParser;
import parsers.xsd.XsdElement;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 1 on 14.11.2016.
 */
public class JacksonParser {
    static String json = "{\n" +
            "  \"firstName\": \"Иван\",\n" +
            "  \"lastName\": \"Иванов\",\n" +
            "  \"address\": {\n" +
            "    \"streetAddress\": \"Московское ш., 101, кв.101\",\n" +
            "    \"city\": \"Ленинград\" ,\n" +
            "    \"postalCode\": 10110.1\n" +
            "  },\n" +
            "  \"phoneNumbers\": [\n" +
            "    \"812 123-1234\",\n" +
            "    \"916 123-4567\"\n" +
            "  ]\n" +
            "}";
    static ArrayList<XsdElement> xsdElementList = new ArrayList<>(StaxXsdParser.getElementsArrayList());
    static ArrayList<String> primitiveTypeList = new ArrayList<String>() {{
        add("string");
        add("integer");
        add("boolean");
    }};

    private static void showXsdElements() {
        xsdElementList.forEach(System.out::println);
    }

    public static void parsJson() throws IOException {
        //showXsdElements();

        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(json);
        JsonToken jsonToken = jsonParser.nextToken();
        if (jsonToken == null) {
            // return or throw exception
        }
        while (true) {
            jsonToken = jsonParser.nextToken();
            if (jsonToken == null) {
                break;
            }
            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String name = jsonParser.getText();
                checkTypes(name, jsonParser);
            }
            // parse your objects by means of parser.getXxxValue() and/or other parser's methods
        }
    }

    private static void checkTypes(String name, JsonParser jsonParser) throws IOException {
        for (XsdElement element : xsdElementList) {
            if (element.getName().equalsIgnoreCase(name)) {
                String type = element.getType();
                if (!primitiveTypeList.contains(type) || type.equals(""))
                    continue;
                JsonToken jsonToken = jsonParser.nextToken();
                if (type.equalsIgnoreCase("string") && !(JsonToken.VALUE_STRING.equals(jsonToken)))
                    System.out.println("Type error : " + type + " and " + jsonParser.getText());
                if (type.equalsIgnoreCase("integer") && !(JsonToken.VALUE_NUMBER_INT.equals(jsonToken)))
                    System.out.println("Type error : " + type + " and " + jsonParser.getText());
                if (type.equalsIgnoreCase("boolean") && !(JsonToken.VALUE_FALSE.equals(jsonToken) || JsonToken.VALUE_TRUE.equals(jsonToken)))
                    System.out.println("Type error : " + type + " and " + jsonParser.getText());
            }
        }

    }
}
