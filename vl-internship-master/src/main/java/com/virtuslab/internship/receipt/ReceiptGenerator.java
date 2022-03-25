package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<Product> products = basket.getProducts();
        Map<Product, Integer> map = new HashMap<>();
        for (Product p : products) {
            map.put(p, map.getOrDefault(p, 0) + 1);
        }
        map.forEach((k, v) -> {
            ReceiptEntry receiptEntry = new ReceiptEntry(k, v);
            receiptEntries.add(receiptEntry);
        });
        return new Receipt(receiptEntries);
    }
}
