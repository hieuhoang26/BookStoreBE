package com.bookstore.modules.order.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    Integer id;
    LocalDate date;
    Double totalPrice;
    String DeliveryAddress;
    String orderStatus;
    Boolean isConfirm;
    Boolean isEvaluate;
}
