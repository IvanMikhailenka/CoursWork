package validators;

import parsers.json.JacksonParser;
import parsers.xsd.Parser;
import parsers.xsd.StaxXsdParser;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 1 on 15.11.2016.
 */
public class Validator {
    private static Parser xsdParser = new StaxXsdParser();
    public static void main(String... args){
        try {
            xsdParser.StaxParser("C:/json.xsd");
            JacksonParser.parsJson();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
