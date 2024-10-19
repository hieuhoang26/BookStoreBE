package com.bookstore.common.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CART_ITEM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class CartItem extends Common<Integer>{
    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "CART_ID" , nullable = true)
    private Cart cart;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public CartItem(Integer quantity, Book book) {
        this.quantity = quantity;
        this.book = book;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "quantity=" + quantity +
                ", cart=" + cart +
                ", book=" + book +
                '}';
    }
}
