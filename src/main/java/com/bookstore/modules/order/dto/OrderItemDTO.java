package com.bookstore.modules.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Integer id;
    private String bookTitle;
    private String image;
    private Integer quantity;


}
