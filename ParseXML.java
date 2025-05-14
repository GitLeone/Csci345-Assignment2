import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXML{
    //setList will hold all the newly created sets
    //private List<Set> setList;
    private Map<String, Set> setList;
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
        //Nodes
        for (int i=0; i < cards.getLength(); i++){
            Node card = cards.item(i);
            String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println("Name = " + cardName);
            String cardBudget = card.getAttributes().getNamedItem("budget").getNodeValue();
            System.out.println("Budget = " + cardBudget);
            //Nodes Children
            NodeList children = card.getChildNodes();
            for (int j = 0; j < children.getLength(); j++){
                Node sub = children.item(j);
                if ("scene".equals(sub.getNodeName())){
                    int sceneNumber = Integer.parseInt(sub.getAttributes().getNamedItem("number").getNodeValue());
                    System.out.println("Scene number = " + sceneNumber);
                    String scene = sub.getTextContent().trim();
                    System.out.println("Scene = " + scene);
                }
                else if ("part".equals(sub.getNodeName())){
                    String partName = sub.getAttributes().getNamedItem("name").getNodeValue();
                    int partLevel = Integer.parseInt(sub.getAttributes().getNamedItem("level").getNodeValue());
                    System.out.println("Part name = " + partName);
                    System.out.println("Part level = " + partLevel);

                    //This gets the children of the part child
                    NodeList partChildren = sub.getChildNodes();
                    for (int k = 0; k < partChildren.getLength(); k++){
                        Node partSub = partChildren.item(k);
                        if("line".equals(partSub.getNodeName())){
                            String partLine = partSub.getTextContent().trim();
                            System.out.println("Line = " + partLine);                            
                        }
                    }
                }
            }
        }
    }

    public void readBoardData(Document d){
        Element root = d.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");
        //Nodes
        for (int i=0; i < sets.getLength(); i++){
            Node set = sets.item(i);
            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
            //Create new set here
            Set newSet = new Set(setName, 0);
            this.setList.add(setName, newSet);
            //key = setName
            //Nodes Children
            NodeList children = set.getChildNodes();
            for (int j = 0; j < children.getLength(); j++){
                Node sub = children.item(j);
                if("neighbors".equals(sub.getNodeName())){
                    //neighbors children
                    NodeList neighborsChildren = sub.getChildNodes();
                    for (int k = 0; k < neighborsChildren.getLength(); k++){
                        Node neighborsSub = neighborsChildren.item(k);
                        if("neighbor".equals(neighborsSub.getNodeName())){
                            String neighborName = neighborsSub.getAttributes().getNamedItem("name").getNodeValue();
                            //Add to the value list of neighbor for the associated set
                            newSet.addAdjacentSet(neighborName);
                        }
                    }
                }
                else if("takes".equals(sub.getNodeName())){
                    NodeList takesChildren = sub.getChildNodes();
                    int maxTakes = 0;
                    for (int k = 0; k < takesChildren.getLength(); k++){
                        Node takesSub = takesChildren.item(k);
                        if("take".equals(takesSub.getNodeName())){
                           int numTakes = Integer.parseInt(takesSub.getAttributes().getNamedItem("number").getNodeValue());
                            if (numTakes > maxTakes){
                                maxTakes = numTakes;
                            }
                        }
                    }
                    //sets Set objects shots remaining
                    //Using a max method so I can more easily make chanegs when we need the positions of multiple shots in Assignment 3
                    newSet.setShotsRemaining(maxTakes);
                }
                else if("parts".equals(sub.getNodeName())){
                    NodeList partsChildren = sub.getChildNodes();
                    for (int k = 0; k < partsChildren.getLength(); k++){
                        Node partsSub = partsChildren.item(k);
                        if("part".equals(partsSub.getNodeName())){
                            String partName = partsSub.getAttributes().getNamedItem("name").getNodeValue();
                            int partLevel = Integer.parseInt(partsSub.getAttributes().getNamedItem("level").getNodeValue());
                            //creates the off card roles
                            Role newRole = new Role(partName, false, partLevel);
                            //adds the off card role to the associated set
                            newSet.addOffRole(newRole);
                            NodeList partChildren = partsSub.getChildNodes();
                            for (int l = 0; l < partChildren.getLength(); l++){
                                Node partSub = partChildren.item(l);
                                if("line".equals(partSub.getNodeName())){
                                    //Probably going to change partsSub to partSub here. Need testing
                                    String partLine = partsSub.getTextContent().trim();
                                    //Sets the line of all off card roles for the set
                                    newRole.setLine(partLine);
                                }
                            }
                        }
                    }
                }
            }
        }
        Node trailer = root.getElementsByTagName("trailer").item(0);
        System.out.println("Set name = Trailer");
        //Nodes Children
        NodeList trailerChildren = trailer.getChildNodes();
        for (int i=0; i < trailerChildren.getLength(); i++){
            Node sub = trailerChildren.item(i);
            if("neighbors".equals(sub.getNodeName())){
                //neighbors children
                NodeList neighborsChildren = sub.getChildNodes();
                for (int j = 0; j < neighborsChildren.getLength(); j++){
                    Node neighborsSub = neighborsChildren.item(j);
                    if("neighbor".equals(neighborsSub.getNodeName())){
                        String neighborName = neighborsSub.getAttributes().getNamedItem("name").getNodeValue();
                        System.out.println("Neighbor name = " + neighborName);
                    }
                }
            }
        }
        Node office = root.getElementsByTagName("office").item(0);
        System.out.println("Set name = Office");
        //Nodes Children
        NodeList officeChildren = office.getChildNodes();
        for (int i=0; i < officeChildren.getLength(); i++){
            Node sub = officeChildren.item(i);
            if("neighbors".equals(sub.getNodeName())){
                //neighbors children
                NodeList neighborsChildren = sub.getChildNodes();
                for (int j = 0; j < neighborsChildren.getLength(); j++){
                    Node neighborsSub = neighborsChildren.item(j);
                    if("neighbor".equals(neighborsSub.getNodeName())){
                        String neighborName = neighborsSub.getAttributes().getNamedItem("name").getNodeValue();
                        System.out.println("Neighbor name = " + neighborName);
                    }
                }
            }
            else if("upgrades".equals(sub.getNodeName())){
                NodeList upgradesChildren = sub.getChildNodes();
                for (int j = 0; j < upgradesChildren.getLength(); j++){
                    Node upgradesSub = upgradesChildren.item(j);
                    if("upgrade".equals(upgradesSub.getNodeName())){
                        int upgradeLevel = Integer.parseInt(upgradesSub.getAttributes().getNamedItem("level").getNodeValue());
                        System.out.println("Upgrade level = " + upgradeLevel);
                        String upgradeCurrency = upgradesSub.getAttributes().getNamedItem("currency").getNodeValue();
                        System.out.println("Currency = " + upgradeCurrency);
                        int upgradeCost = Integer.parseInt(upgradesSub.getAttributes().getNamedItem("amt").getNodeValue());
                        System.out.println("Upgrade cost = " + upgradeCost);
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        ParseXML parse = new ParseXML();
        try {
            Document doc = parse.getDocFromFile(args[0]);
            //parse.readCardData(doc);
            parse.readBoardData(doc);
        }
        catch(ParserConfigurationException e){
            System.err.println("Error");
        }
    }
}

