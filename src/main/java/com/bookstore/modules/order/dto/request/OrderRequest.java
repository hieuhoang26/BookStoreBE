package com.bookstore.modules.order.dto.request;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest implements Serializable {
    @NotNull
    Integer userId;

    @NotNull
    Integer shopId;

    @NotNull
    @PositiveOrZero
    Double totalPrice;

    String address;


    List<OrderItemRequest> orderItems;
}
