package com.virtuslab.internship.receipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReceiptAfterDiscountGeneratorTest {

    @Autowired
    private TestRestTemplate template;
    /*To run the tests, please use ./gradlew bootRun(Mac) or .\gradlew bootRun(Windows)
     *in the complete directory first.*/

    @Test
    public void receiptGeneratorApplyOnlyTenPercentDiscount() throws JSONException, JsonProcessingException {

        //Products
        List<Product> products = new ArrayList<>();
        var productDb = new ProductDb();
        var apple = productDb.getProduct("Apple");
        var bread = productDb.getProduct("Bread");
        var cheese = productDb.getProduct("Cheese");

        products.add(apple);
        products.add(cheese);
        products.add(cheese);
        products.add(cheese);
        products.add(bread);
        products.add(bread);
        products.add(bread);

        //RequestEntity
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.APPLICATION_JSON);
        headers.setAccept(list);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(products, headers);

        //ResponseEntity
        ResponseEntity<Receipt> responseEntity =
                template.exchange(
                        "http://localhost:8080/basket/generateReceipt",
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<Receipt>() {}
                );
        Receipt receipt = responseEntity.getBody();

        //test
        var expectedTotalPrice = apple.price().multiply(BigDecimal.valueOf(1))
                .add(cheese.price().multiply(BigDecimal.valueOf(3)))
                .add(bread.price().multiply(BigDecimal.valueOf(3)))
                .multiply(BigDecimal.valueOf(0.85) // 15% percent discount
                .multiply(BigDecimal.valueOf(0.9))); // 10% percent discount

        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(2, receipt.discounts().size());
    }
}
