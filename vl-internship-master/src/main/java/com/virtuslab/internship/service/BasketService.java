package com.virtuslab.internship.service;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    @Autowired
    private ReceiptGenerator receiptGenerator;

    @Autowired
    private FifteenPercentDiscount fifteenPercentDiscount;

    @Autowired
    private TenPercentDiscount tenPercentDiscount;


    public Receipt generateReceipt(List<Product> products) {
        Basket basket = new Basket(products);
        var receipt = receiptGenerator.generate(basket);
        var receiptAfterDiscount = tenPercentDiscount.apply(fifteenPercentDiscount.apply(receipt));
        return receiptAfterDiscount;
    }
}
