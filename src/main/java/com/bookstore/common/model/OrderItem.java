package com.bookstore.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "ORDER_ITEM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class OrderItem extends  Common<Integer>  {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ORDER_ID" , nullable = true)
    private Order order;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "BOOK_ID")
    private Book book;


    @Column(name = "QUANTITY")
    private Integer quantity;

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + getId() +
                ", order=" + order +
                ", book=" + book +
                ", quantity=" + quantity +
                '}';
    }
    public OrderItem(Book book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
    }


}
