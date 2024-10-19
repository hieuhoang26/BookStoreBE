package com.bookstore.modules.order.dto.request;

import com.bookstore.modules.cart.dto.CartRequest;
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
    @NotNull Integer userId;

    @NotNull @PositiveOrZero Double totalPrice;

    String address;

    Integer statusOrder;

    List<Item> orderItems;

    @Setter
    @Getter
    public static class Item {
        private Integer bookId;
        @NotNull
        private Integer shopId;
        private Integer quantity;
    }
}
