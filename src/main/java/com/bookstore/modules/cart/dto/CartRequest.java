package com.bookstore.modules.cart.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest implements Serializable {
    @NotNull
    Integer userId;
    @NotNull
    Integer bookId;

    @NotNull
    @Positive
    Integer quantity;
}
