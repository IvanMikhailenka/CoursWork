package parsers.xsd;

/**
 * Created by Asus on 03.04.2016.
 */
public class XsdElement {
    private String name;
    private String type;
    private int minOccurs;
    private int maxOccurs;
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public XsdElement(String name, String type, int minOccurs, int maxOccurs) {
        this.name = name;
        this.type = type;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    @Override
    public String toString() {
        return "XsdElement : "  +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", minOccurs=" + minOccurs +
                ", maxOccurs=" + maxOccurs;
    }
}
