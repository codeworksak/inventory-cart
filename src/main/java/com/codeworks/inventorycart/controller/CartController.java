package com.codeworks.inventorycart.controller;

import com.codeworks.inventorycart.models.CartItem;
import com.codeworks.inventorycart.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<CartItem> add(@RequestBody CartItem cartItem, @PathVariable String userId)
    {
        cartService.addCartItem(userId,cartItem);

        return ResponseEntity.created(ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{userId").buildAndExpand(cartItem.getProductId()).toUri())
                .body(cartItem);
    }

    @GetMapping("/get/{userid}")
    public ResponseEntity<Map<String,CartItem>> getCart(@PathVariable String userid )
    {
        return ResponseEntity.ok(cartService.getCart(userid));
    }

    @DeleteMapping("/remove/{userid}/{pid}")
    public ResponseEntity<Boolean> remove(@PathVariable String userid, @PathVariable String pid)
    {
        cartService.removeCartItem(userid,pid);
        return  ResponseEntity.ok(true);
    }
    @DeleteMapping("/remove-cart/{userid}")
    public ResponseEntity<Boolean> removeCart(@PathVariable String userid)
    {
        System.out.println(userid);
        cartService.deleteCart(userid);
        return  ResponseEntity.ok(true);
    }

}
