package com.bookstore.common.service.serviceImp;

import com.bookstore.common.exception.ResourceNotFoundExcep;
import com.bookstore.common.model.*;
import com.bookstore.common.repository.CartRepository;
import com.bookstore.common.repository.OrderItemRepository;
import com.bookstore.common.service.BookService;
import com.bookstore.common.service.CartService;
import com.bookstore.common.service.UserService;
import com.bookstore.modules.cart.dto.CartRequest;
import com.bookstore.modules.cart.dto.CartResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImp implements CartService {
    final OrderItemRepository orderItemRepository;
    final CartRepository cartRepository;
    final UserService userService;
    final BookService bookService;

    @Override
    public CartResponse getCartByUserId(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return new CartResponse(userId, null, Collections.emptyList());
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setUserId(cart.getUser().getId());
        cartResponse.setCartId(cart.getId());

        List<CartResponse.Item> items = cart.getCartItems().stream().map(cartItem -> {
            CartResponse.Item item = new CartResponse.Item();
            item.setBookId(cartItem.getBook().getId());
            item.setShopId(cartItem.getBook().getShop().getId());
            item.setTitle(cartItem.getBook().getTitle());
            item.setPrice(cartItem.getBook().getPrice());
            item.setCurrentQuantity(cartItem.getBook().getCurrentQuantity());
            Set<BookImage> bookImages = cartItem.getBook().getBookImages();
            BookImage firstImage = bookImages.iterator().next();
            item.setImagePath(firstImage.getImagePath());
            item.setQuantity(cartItem.getQuantity());
            return item;
        }).collect(Collectors.toList());

        cartResponse.setItemList(items);
        return cartResponse;

    }


    @Override
    public void addToCart(CartRequest request) {
        User user = userService.getUserById(request.getUserId());
        Book book = bookService.getBookById(request.getBookId());

        Cart cart = cartRepository.findByUserId(request.getUserId());

        if (cart != null) {
            Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                    .filter(item -> item.getBook().getId().equals(book.getId()))
                    .findFirst();

            if (existingCartItem.isPresent()) {
                CartItem cartItem = existingCartItem.get();
                cartItem.setQuantity(request.getQuantity());
            } else {
                CartItem cartItem = new CartItem(request.getQuantity(), book);
                cart.addCartItem(cartItem);
            }
        } else {
            cart = new Cart();
            cart.setUser(user);
            CartItem cartItem = new CartItem(request.getQuantity(), book);
            cart.addCartItem(cartItem);
        }
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deteleBook(Integer userId, Integer[] bookIds) {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new ResourceNotFoundExcep("Cart not found for user id: " + userId);
        }
        for (Integer bookId : bookIds) {
            Optional<CartItem> cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getBook().getId().equals(bookId))
                    .findFirst();

            if (cartItem.isEmpty()) {
                throw new ResourceNotFoundExcep("Book not found in the cart");
            }
            cart.getCartItems().remove(cartItem.get());
            cartRepository.save(cart);
        }

    }

}
