package com.bookstore.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_detail")
public class BookDetail extends Common<Integer>{
    @NotBlank
    @Column(name = "PUBLISHER")
    private String publisher;
    @NotNull
    @Column(name = "PUBLICATION_DATE")
    private LocalDate publicationDate;

    @Column(name = "DIMENSIONS")
    private String dimensions;

    @Column(name = "COVER_TYPE")
    private String coverType;

    @Column(name = "NUMBER_PAGES")
    private Integer numberPages;

    @Column(name = "DESCRIPTION")
    private String description;


    /* <------------------ Mapping --------------------> */
    // 1-1 Book
    /* Delete BookDetails, Does Not Delete Book */
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bookDetail", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    Book book;


    /* <------------------ Entity Method -------------------> */

    @Override
    public String toString() {
        return "BookDetail{" +
                "publisher='" + publisher + '\'' +
                ", publicationDate=" + publicationDate +
                ", dimensions='" + dimensions + '\'' +
                ", coverType='" + coverType + '\'' +
                ", numberPages=" + numberPages +
                ", description='" + description + '\'' +
                '}';
    }
}
