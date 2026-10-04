package com.codeworks.inventorycart.controller;

import com.codeworks.inventorycart.configs.RabbitConfig;
import com.codeworks.inventorycart.models.CartItem;
import com.codeworks.inventorycart.services.CartService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
    @PostMapping("/checkout/{userId}")
    public ResponseEntity<?> checkout(@PathVariable String userId )
    {
        Map<String,CartItem> cartItemms = cartService.getCart(userId);
       List<CartItem> items =cartItemms.values().stream().toList();

       rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,
               RabbitConfig.ROUTING_KEY,items);
       return  ResponseEntity.ok("Cart checkedout successfully");
    }

}
