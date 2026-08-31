// The Adapter Pattern is a structural design pattern that allows incompatible interfaces to collaborate. It acts as a wrapper or bridge between two classes that cannot interact directly due to mismatched method signatures, return types, or data formats.

// Core Components of the Pattern

// Target (IReports): The standard domain-specific interface that your client code knows and depends on (expects JSON data via getJsonData).

// Adaptee (XmlDataProvider): An existing or third-party service with valuable functionality, but with an incompatible interface (produces XML data via getXmlData).

// Adapter (XmlDataProviderAdapter): The bridge class that implements the Target interface (is-a relationship) and holds an instance of the Adaptee (has-a relationship). It translates incoming requests into a format the Adaptee understands, then converts the output back into the format the client expects.

// Client (Client): The consumer class that interacts exclusively with the Target interface, staying completely decoupled from third-party or legacy classes.

// Main entry point - Copy-paste and run directly
public class Main {
    public static void main(String[] args) {
        // 1. Instantiate the Adaptee (Third-party class producing XML)
        XmlDataProvider xmlProvider = new XmlDataProvider();

        // 2. Wrap the Adaptee inside the Adapter
        IReports adapter = new XmlDataProviderAdapter(xmlProvider);

        // 3. Client consumes the service via the Target interface
        Client client = new Client();
        String rawData = "Alice:42";

        System.out.println("=== Client Requesting JSON Report ===");
        client.getReport(adapter, rawData);
    }
}

// 1. Target Interface: The contract expected by the client
interface IReports {
    String getJsonData(String data);
}

// 2. Adaptee: Incompatible third-party or legacy class
class XmlDataProvider {
    public String getXmlData(String data) {
        String[] parts = data.split(":");
        String name = parts.length > 0 ? parts[0] : "";
        String id = parts.length > 1 ? parts[1] : "";

        return "<user><name>" + name + "</name><id>" + id + "</id></user>";
    }
}

// 3. Adapter: Implements Target ("is-a") and wraps Adaptee ("has-a")
class XmlDataProviderAdapter implements IReports {
    private final XmlDataProvider xmlDataProvider;

    public XmlDataProviderAdapter(XmlDataProvider xmlDataProvider) {
        this.xmlDataProvider = xmlDataProvider;
    }

    @Override
    public String getJsonData(String data) {
        // Fetch data from the incompatible Adaptee
        String xmlData = xmlDataProvider.getXmlData(data);

        // Convert the Adaptee's XML output to the Target's expected JSON format
        return convertXmlToJson(xmlData);
    }

    private String convertXmlToJson(String xml) {
        String name = extractTag(xml, "name");
        String id = extractTag(xml, "id");

        return "{\n  \"name\": \"" + name + "\",\n  \"id\": " + id + "\n}";
    }

    private String extractTag(String xml, String tag) {
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";
        int start = xml.indexOf(openTag);
        int end = xml.indexOf(closeTag);

        if (start != -1 && end != -1) {
            return xml.substring(start + openTag.length(), end);
        }
        return "";
    }
}

// 4. Client: Depends solely on the Target abstraction
class Client {
    public void getReport(IReports reportService, String rawData) {
        String jsonResult = reportService.getJsonData(rawData);
        System.out.println("Received JSON Output:\n" + jsonResult);
    }
}
