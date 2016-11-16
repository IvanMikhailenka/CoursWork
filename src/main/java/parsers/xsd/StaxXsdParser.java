package parsers.xsd;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 08.04.2016.
 */

public class StaxXsdParser extends Parser {
    private static final QName NAME = new QName("name");
    private static final QName TYPE = new QName("type");
    private static final QName MIN_OCCURS = new QName("minOccurs");
    private static final QName MAX_OCCURS = new QName("maxOccurs");
    //private static HashMap<String, ArrayList<XsdElement>> superMap = new HashMap<String, ArrayList<XsdElement>>();
    //private static HashMap<XsdElement, ArrayList<XsdElement>> superPuperMap = new HashMap<XsdElement, ArrayList<XsdElement>>();
    private static ArrayList<XsdElement> elementsArrayList = new ArrayList<XsdElement>();

    @Override
    protected void logic(XMLEventReader reader) throws XMLStreamException {
        boolean flag = false;
        ArrayList<XsdElement> childList = new ArrayList<XsdElement>();
        String complexName="";
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            String elementLocalName;
            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                elementLocalName = startElement.getName().getLocalPart();
                if(elementLocalName.equals("complexType")){
                    /*childList = new ArrayList<>();
                    complexName = getName(startElement);
                    if(!complexName.equals("null")) {
                        superMap.put(complexName, null);
                        flag = true;
                    }*/
                    continue;
                }
                if(!elementLocalName.equals("element")) continue;
                String name = getName(startElement);
                String type = getType(startElement);
                if(!name.equals("null") && !flag) {
                   // nameAndTypeMap.put(name,type);
                   // superMap.put(name,new ArrayList<String>());
                  //  superPuperMap.put( new XsdElements(name,type,getMinOccurs(startElement),getMaxOccurs(startElement)), null);
                    elementsArrayList.add(new XsdElement(name,type,getMinOccurs(startElement),getMaxOccurs(startElement)));
                   // System.out.println(startElement.getAttributeByName(NAME));
                }
                else if(!name.equals("null") && flag){
                    childList.add(new XsdElement(name,type,getMinOccurs(startElement),getMaxOccurs(startElement)));
                    elementsArrayList.add(new XsdElement(name,type,getMinOccurs(startElement),getMaxOccurs(startElement)));
                }

            }
            /*if(event.isEndElement()){
                EndElement endElement = event.asEndElement();
                elementLocalName ="end: "+ endElement.getName().getLocalPart();
                if(elementLocalName.equals("end: complexType")) {
                    superMap.put(complexName,new ArrayList<XsdElements>(childList));
                    childList.clear();
                    flag = false;
                }
            }*/
        }
//        makeFinaleMap();
    }
/*    private void makeFinaleMap (){
        for(Map.Entry<XsdElement, ArrayList<XsdElement>> entry : superPuperMap.entrySet()){
            if(superMap.containsKey(entry.getKey().getType())){
                superPuperMap.put(entry.getKey(),superMap.get(entry.getKey().getType()));
            }
        }
    }*/
    private String getName (StartElement startElement){
        Attribute nameAttribute = startElement.getAttributeByName(NAME);
        return nameAttribute==null ? "null" : nameAttribute.getValue();
    }
    private String getType (StartElement startElement){
        Attribute typeAttribute = startElement.getAttributeByName(TYPE);
        String type = typeAttribute==null ? "" : typeAttribute.getValue();
        return type.replaceAll("xs:", "");
    }
    private int getMinOccurs(StartElement element){
        Attribute attribute = element.getAttributeByName(MIN_OCCURS);
        if(attribute!=null)
            return Integer.parseInt(attribute.getValue());
        else
            return  1;
    }
    private int getMaxOccurs(StartElement element){
        Attribute attribute = element.getAttributeByName(MAX_OCCURS);
        String Occurs = attribute==null ? "null" : attribute.getValue();
        if(!Occurs.equals("null") && !Occurs.equals("unbounded"))
            return Integer.parseInt(Occurs);
        else
            return Integer.MAX_VALUE;
    }
    public static ArrayList<XsdElement> getElementsArrayList() {
        return elementsArrayList;
    }
}
