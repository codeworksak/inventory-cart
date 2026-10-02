package com.codeworks.inventorycart.services;

import com.codeworks.inventorycart.models.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CartService {
//    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    private HashOperations<String,String,CartItem> hashOperations; //redisTemplate.opsForHash();
    private String cartId;

    public CartService(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate=redisTemplate;
        hashOperations=redisTemplate.opsForHash();
    }

    public void addCartItem(String userId, CartItem cartItem) {
        String key = "cart:" + userId;
        Object cachedata=hashOperations.get(key,cartItem.getProductId());

        if(cachedata !=null)
        {
            CartItem ci = objectMapper.convertValue(cachedata,CartItem.class);
            ci.setQuantity(ci.getQuantity() + cartItem.getQuantity());
            hashOperations.put(key,cartItem.getProductId().toString(),ci);
        }else
            hashOperations.put(key,cartItem.getProductId().toString(),cartItem);

        redisTemplate.expire(key,7, TimeUnit.DAYS);
    }
    public Map<String,CartItem> getCart(String userId)
    {
        String key = "cart:" + userId;
        return  hashOperations.entries(key);
    }
    public void removeCartItem(String userId,String pid)
    {
        String key = "cart:" + userId;
        hashOperations.delete(key,pid);
    }
    public  void  deleteCart(String userId)
    {
        String key = "cart:" + userId;
        redisTemplate.delete(key);
    }
}
