package com.bookstore.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "book")
public class Book extends Common<Integer>{

    @NotBlank
    @Size(min = 5, max = 255)
    @Column(name = "TITLE",unique = true)
    private String title;

    @NotNull
    @PositiveOrZero
    @Column(name = "PRICE")
    private Double price;
    @NotNull
    @Column(name = "AUTHOR")
    private String author;

    @NotNull
    @Column(name = "CURRENT_QUANTITY")
    private Integer currentQuantity;

    @NotNull
    @Column(name = "SOLD_QUANTITY")
    private Integer soldQuantity;

    /*-----------Mapping--------------------*/
    /* n-1 User */
    /* Delete Book, Does Not Delete User */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    User user;

    /* (n-1) Shop */
    /* Delete Book, Does Not Delete Shop */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "shop_id", referencedColumnName = "ID")
    Shop shop;

    // (1-1) BookDetail
    /* Delete Book, Delete BookDetails */
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_detail_id")
    BookDetail bookDetail;

    // (1-n) BookImage
    // Delete Book Delete Image
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    Set<BookImage> bookImages;


    //(n-n) Category
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_category",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    Set<Category> categories;

    /* 1-n OrderItem */
    /* Delete Book, Delete Order */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.PERSIST)
    Set<OrderItem> orderItems;

    /* 1-n Review */
    /* Delete Book, Delete Reviews */
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book", cascade = CascadeType.ALL)
    Set<Review> reviews;

    /* 1-n Rate */
    /* Delete Book, Delete Rate */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    Set<Rate> rates;

    /*-----------Convert Method--------------------*/
    public void addCategory(Category category) {
        if (category != null) {
            if (categories == null) {
                categories = new HashSet<>();
            }
            categories.add(category);
            category.getBooks().add(this); // save user_id
        }
    }
    public void addBookImage(BookImage bookImage){
        if(bookImages == null) bookImages = new HashSet<>();
        bookImages.add(bookImage);
        bookImage.setBook(this);
    }

    /*-----------Method--------------------*/

    public Book(String title, Double price, String author, Integer currentQuantity, Integer soldQuantity) {
        this.title = title;
        this.price = price;
        this.author = author;
        this.currentQuantity = currentQuantity;
        this.soldQuantity = soldQuantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id = " + getId() +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                ", currentQuantity=" + currentQuantity +
                ", soldQuantity=" + soldQuantity +
                '}';
    }
}
