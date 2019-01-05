package pl.arturzaczek.demo.product;

import org.junit.Test;

import java.util.List;

public class ProductParserTest {

    @Test
    public void checkXMLParseMethod() {

        ProductDTO productDTO = new ProductDTO();
        List<Product> testList = productDTO.parseXMLToProductList();

        for (Product p : testList) {
            System.out.println(p.toString());
        }
    }
}
