package com.virtuslab.internship.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public record Product(
        @JsonProperty("name")String name,
        @JsonProperty("type")Type type,
        @JsonProperty("price")BigDecimal price
) {
    public enum Type {
        DAIRY, FRUITS, VEGETABLES, MEAT, GRAINS
    }
}
