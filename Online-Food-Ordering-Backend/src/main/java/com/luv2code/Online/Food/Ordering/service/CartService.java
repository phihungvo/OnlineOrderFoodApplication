package com.luv2code.Online.Food.Ordering.service;

import com.luv2code.Online.Food.Ordering.model.CartItem;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception;



}
