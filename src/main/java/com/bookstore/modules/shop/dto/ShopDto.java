package com.bookstore.modules.shop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopDto {
    Integer id;
    String shopName;
    String shopLogo;
    String shopAddress;
    String contactEmail;
    String contactPhone;

}
