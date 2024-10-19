package com.bookstore.common.service;

import com.bookstore.modules.cart.dto.CartRequest;
import com.bookstore.modules.cart.dto.CartResponse;

public interface CartService {
    CartResponse getCartByUserId(Integer id);
    void addToCart(CartRequest orderItemRequest);
    void deteleBook(Integer userId, Integer[] bookId);
//    void detele(Integer userId);


}
