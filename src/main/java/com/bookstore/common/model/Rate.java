package com.bookstore.common.model;

import com.bookstore.common.model.compositekey.UserBookRate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Builder
@AllArgsConstructor
@Table(name = "rates")
public class Rate {
    @EmbeddedId
    UserBookRate id;

    /* Delete Rate, Does Not Delete User */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    User user;

    /* Delete Rate, Does Not Delete Book */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("bookId")
    @JoinColumn(name = "book_id", referencedColumnName = "ID")
    Book book;

    @NotNull
    @PositiveOrZero
    @Range(max = 5, min = 0)
    @Column(name = "rating")
    Integer rating;

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", rating=" + rating +
                '}';
    }
}
