package com.bookstore.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "REVIEW")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Review extends  Common<Integer>{

    /* Delete Review, Does Not Delete User */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName = "ID")
    User user;

    /* Delete Review, Does Not Delete Book */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "book_id", referencedColumnName = "ID")
    Book book;

    @Size(min = 5, max = 255)
    @Column(name = "COMMENT")
    private String comment;


    @Column(name = "IMAGE")
    private String image;

    @Override
    public String toString() {
        return "Review{" +
                "id = " + getId() +
                "comment='" + comment + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
