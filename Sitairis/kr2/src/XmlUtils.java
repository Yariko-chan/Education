import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class XmlUtils {

    public static String validate(String dataFilePath, String schemaFilePath) {
        String result = null;
        Source xmlFile = null;
        try {
            File schemaFile = new File(schemaFilePath);
            xmlFile = new StreamSource(new File(dataFilePath));
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            // valid, return no null
        } catch (SAXException e) {
            result = xmlFile.getSystemId() + " is NOT valid reason:" + e;
        } catch (MalformedURLException e) {
            result = "Error in schema URL: " + e;
        } catch (IOException e) {
            result = "Error reading xml file: " + e;
        }
        return result;
    }

    public static List<Ticket> parseTicketsFromXml(String s) {
        List<Ticket> items = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(s)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("ticket");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Ticket b = new Ticket.Builder()
                            .setArrivalTime(Long.valueOf(eElement.getAttribute("arrivalTime")))
                            .setDepartureTime(Long.valueOf(eElement.getAttribute("departureTime")))
                            .setFrom(eElement.getAttribute("from"))
                            .setTo(eElement.getAttribute("to"))
                            .setRouteNum(Integer.valueOf(eElement.getAttribute("routeNum")))
                            .setSellDate(Long.valueOf(eElement.getAttribute("sellTime")))
                            .setPassengerName(eElement.getAttribute("passengerName"))
                            .ticket();
                    items.add(b);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
        }
        return items;
    }

    public static List<Ticket> addToXml(String filename, Ticket t) {
        List<Ticket> items = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(filename);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("tickets");
            Element e = (Element) nList.item(0);

            Element ticket = doc.createElement("ticket");
            ticket.setAttribute("arrivalTime", String.valueOf(t.getArrivalTime()));
            ticket.setAttribute("departureTime",  String.valueOf(t.getDepartureTime()));
            ticket.setAttribute("from", t.getFrom());
            ticket.setAttribute("to", t.getTo());
            ticket.setAttribute("routeNum",  String.valueOf(t.getRouteNum()));
            ticket.setAttribute("sellTime",  String.valueOf(t.getSellDate()));
            ticket.setAttribute("passengerName", t.getPassengerName());
            e.appendChild(ticket);

            DOMSource source = new DOMSource(doc);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(filename);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return items;
    }
}
