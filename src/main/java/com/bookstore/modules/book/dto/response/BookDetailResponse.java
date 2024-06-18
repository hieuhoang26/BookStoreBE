package com.bookstore.modules.book.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDetailResponse {
    Integer id;
    Integer shopId;
    String shopName;
    String title;
    Double price;
    String author;
    Integer currentQuantity;
    Integer soldQuantity;

    String publisher;
    LocalDate publicationDate;
    String dimension;
    String coverType;
    Integer numberOfPages;
    String description;

    List<String> categories;
    List<String> images;
}
