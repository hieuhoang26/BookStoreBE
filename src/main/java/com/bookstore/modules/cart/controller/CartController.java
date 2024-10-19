package com.bookstore.modules.cart.controller;

import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.service.CartService;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.cart.dto.CartRequest;
import com.bookstore.modules.cart.dto.CartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping(value = Uri.CART + "/{id}")
    public ResponseData<CartResponse> getCartByUserId(@PathVariable Integer id){
        return new ResponseData<>(HttpStatus.OK.value(),"Get cart by UserId", cartService.getCartByUserId(id));
    }
    @PostMapping(value = Uri.CART)
    public ResponseData<CartResponse> addToCart(@RequestBody CartRequest request){
        cartService.addToCart(request);
        return new ResponseData<>(HttpStatus.OK.value(),"Add success");
    }
//    @DeleteMapping(value = Uri.CART+ "/{id}")
//    public ResponseData<CartResponse> deleteCart(@PathVariable Integer userId){
//        cartService.detele(userId);
//        return new ResponseData<>(HttpStatus.OK.value(),"Delete success");
//    }
    @DeleteMapping(value = Uri.CART)
    public ResponseData<CartResponse> deleteBookCart(@RequestParam Integer userId,@RequestParam Integer[] bookId){
        cartService.deteleBook(userId,bookId);
        return new ResponseData<>(HttpStatus.OK.value(),"Delete book success");
    }


}
