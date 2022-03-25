package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenPercentDiscountTest {
    //Case 1 we should only apply 15 percent discount
    @Test
    void shouldOnlyApply15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 3));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var tenPercentDiscount = new TenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(3)).add(cereals.price()).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = tenPercentDiscount.apply(fifteenPercentDiscount.apply(receipt));
        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
    //case 2 we should apply both 15 and 10 percent discount
    @Test
    void shouldApplyBoth15And10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 30));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var tenPercentDiscount = new TenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(30)).add(cereals.price()).
                multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));
        // When
        var receiptAfterDiscount = tenPercentDiscount.apply(fifteenPercentDiscount.apply(receipt));

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }
    //case3 we should only apply 10 Percent Discount
    @Test
    void shouldOnlyApply10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var steak = productDb.getProduct("Steak");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(steak, 30));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var tenPercentDiscount = new TenPercentDiscount();
        var expectedTotalPrice = steak.price().multiply(BigDecimal.valueOf(30))
                .add(cereals.price()).multiply(BigDecimal.valueOf(0.9));
        // When

        var receiptAfterDiscount = tenPercentDiscount.apply(fifteenPercentDiscount.apply(receipt));

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
    //case 4 we should apply neither 15 and 10 percent discount
    @Test
    void shouldApplyNeither15And10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var tenPercentDiscount = new TenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(1)).add(cereals.price());

        // When
        var receiptAfterDiscount = tenPercentDiscount.apply(fifteenPercentDiscount.apply(receipt));

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}
