package com.virtuslab.internship.controller;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/generateReceipt")
    @ResponseBody
    public Receipt generateReceipt(@RequestBody List<Product> products) {
        Receipt receipt = basketService.generateReceipt(products);
        System.out.println(receipt);
        return receipt;
    }
}
