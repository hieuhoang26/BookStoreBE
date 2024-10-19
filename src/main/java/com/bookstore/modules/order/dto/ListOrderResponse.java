package com.bookstore.modules.order.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderResponse {
    private Integer id;
    private String name;
    private String phone;
    private String shoppingAddress;
    private Double totalPrice;
    private String orderStatus;
    private Set<OrderItemDTO> orderItems;
}
