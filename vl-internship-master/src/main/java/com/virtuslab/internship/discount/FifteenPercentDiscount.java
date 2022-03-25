package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FifteenPercentDiscount {

    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
                return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    //There should be at least three grain products
    private boolean shouldApply(Receipt receipt) {
        List<ReceiptEntry> grains = receipt.entries().stream()
                .filter(entry -> entry.product().type().name().equals("GRAINS"))
                .collect(Collectors.toList());
        int grainProductNum = grains.stream()
                .filter(entry -> entry.product().type().name().equals("GRAINS"))
                .mapToInt(ReceiptEntry::quantity)
                .sum();
        return grainProductNum >= 3;
    }
}
