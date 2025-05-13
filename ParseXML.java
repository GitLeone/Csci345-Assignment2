import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXML{
    // returns a Document object
    public Document getDocFromFile(String filename)
    throws ParserConfigurationException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try{
            doc = db.parse(filename);
        } catch (Exception ex){
            System.out.println("XML parse failure");
            ex.printStackTrace();
        }
        return doc;
    } // exception handling

    public void readCardData(Document d){
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");
        for (int i=0; i<cards.getLength(); i++){
            Node card = cards.item(i);
            String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println("Name = "+ cardName);
        }
    }

    public static void main(String[] args) {
        Document doc = getDocFromFile();
        readCardData();
    }
}