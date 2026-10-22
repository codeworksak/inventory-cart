package com.codeworks.inventorycart.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Cart {
    private String userId;
    private List<CartItem> cartItems;
}
