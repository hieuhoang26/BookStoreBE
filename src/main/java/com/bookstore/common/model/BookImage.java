package com.bookstore.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "book_image")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookImage extends  Common<Integer>{
    @NotNull
    @Lob
    @Column(name = "image_path")
    String imagePath;



    /* <------------------ Mapping --------------------> */

    //(n-1) BookImage
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "book_id")
    Book book;
    /* <------------------ Entity Method -------------------> */

    @Override
    public String toString() {
        return "BookImage{" +
                "Id = " + getId() +
                ",imagePath='" + imagePath + '\'' +
                '}';
    }
}
