package com.bookstore.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order extends Common<Integer> {
    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "PHONE")
    private String phone;

    @NotNull
    @Column(name = "SHOPPING_ADDRESS")
    private String shoppingAddress;

    @NotNull
    @PositiveOrZero
    //@Digits(integer = 10, fraction = 3)
    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;


    @Column(name = "ORDER_STATUS")
    private String orderStatus;


    //    (n-1) user
    /* Delete Order, Does Not Delete User */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;


    //    (n-1) Shop
    /* Delete Order, Does Not Delete Shop */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "SHOP_ID", referencedColumnName = "ID")
    private Shop shop;

    /* 1-n OrderItem */
    /* Delete Order, Delete OrderItem */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    Set<OrderItem> orderItems;

    @Override
    public String toString() {
        return "Order{" +
                "totalPrice=" + totalPrice +
//                ", orderDate=" + orderDate +
                ", shoppingAddress='" + shoppingAddress + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
//                ", isConfirm=" + isConfirm +
//                ", isEvaluate=" + isEvaluate +
                '}';
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderItems == null) orderItems = new HashSet<>();
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

}
