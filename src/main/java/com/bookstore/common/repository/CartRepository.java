package com.bookstore.common.repository;

import com.bookstore.common.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findById(Integer id);
    Cart findByUserId(Integer id);
}
