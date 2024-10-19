package com.bookstore.modules.cart.dto;

import com.bookstore.common.model.BookImage;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private Integer userId;
    private Integer cartId;
    private List<Item> itemList;


    public static class Item {
        @Setter
        @Getter
        private Integer bookId;
        @Setter
        @Getter
        private Integer shopId;
        @Setter
        @Getter
        String title;
        @Setter
        @Getter
        Double price;
        @Getter
        @Setter
        Integer currentQuantity;
        @Getter
        @Setter
        String imagePath;
        @Getter
        @Setter
        private Integer quantity;
    }

}
