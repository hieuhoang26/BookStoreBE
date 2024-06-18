package com.bookstore.common.model.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookRate implements Serializable {
    @Column(name = "user_id")
    Integer userId;
    @Column(name = "book_id")
    Integer bookId;
}
