package com.bookstore.modules.book.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {
    Integer id;
    String title;
    Double price;
    String author;
    Integer currentQuantity;
    Integer soldQuantity;
    List<String> imagePath;

}
