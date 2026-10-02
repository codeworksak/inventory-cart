package com.codeworks.inventorycart.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CartItem implements Serializable {
    //@JsonFormat(shape = JsonFormat.Shape.STRING)
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
