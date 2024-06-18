package com.bookstore.modules.shop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopDetailsDto {
    String description;
    String operationHours;
    String shippingPolicy;
    String returnPolicy;
}
