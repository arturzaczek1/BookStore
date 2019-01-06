package pl.arturzaczek.demo.product;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    public static final String PATH = "src\\main\\resources\\books.txt";

    public List<Product> parseXMLToProductList() {
        List<Product> productList = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(PATH);
            NodeList nodeList = doc.getElementsByTagName("book");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Product product = new Product();
                    product.setExternalId(Integer.parseInt((element.getAttribute("id")).replaceAll("bk", "")));
                    product.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
                    product.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
                    product.setGenre(element.getElementsByTagName("genre").item(0).getTextContent());
                    product.setPrice(Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()));
                    product.setPublishDate(LocalDate.parse(element.getElementsByTagName("publish_date").item(0).getTextContent()));
                    product.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
                    productList.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
}
