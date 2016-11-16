package parsers.xsd;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Asus on 23.05.2016.
 */
public abstract class Parser {
    public void StaxParser(String fileName) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader startReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
        XMLEventReader reader = buildFilteredReader(xmlInputFactory, startReader);
        logic(reader);
    }
    protected abstract void logic(XMLEventReader reader) throws XMLStreamException;
    protected XMLEventReader buildFilteredReader(XMLInputFactory xmlInputFactory, XMLEventReader startReader) throws XMLStreamException {
        return xmlInputFactory.createFilteredReader(startReader, new EventFilter() {
            @Override
            public boolean accept(XMLEvent event) {
                if(event.getEventType()== XMLStreamConstants.PROCESSING_INSTRUCTION ||
                        event.getEventType()==XMLStreamConstants.COMMENT)
                    return false;
                else
                    return true;
            }
        });
    }
}
