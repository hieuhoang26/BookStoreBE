package com.bookstore.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop")
public class Shop extends  Common<Integer>{
    @NotBlank
    @Column(name = "SHOP_NAME")
    private String shopName;

    @Column(name = "SHOP_LOGO")
    private String shopLogo;

    @Column(name = "SHOP_ADDRESS")
    private String shopAddress;

    @Email
    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    /*-----------Mapping--------------------*/

    //    (1-1) user
    /* Delete Shop, Does Not Delete User */
    @OneToOne(mappedBy = "shop",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    private User user;

    /* 1-1  ShopDetails */
    /* Delete Shop, Delete ShopDetails */
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_detail_id")
    ShopDetail shopDetail;

    //    (1-n) Order
    /* Delete Shop, Delete Order */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private Set<Order> orders;

    /* 1-n Book */
    /* Delete Shop, Delete Books */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    Set<Book> books;


    /*-----------Method--------------------*/

    @Override
    public String toString() {
        return "Shop{" +
                "Id = " + getId() +
                "shopName='" + shopName + '\'' +
                ", shopLogo='" + shopLogo + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                '}';
    }


    public void addBook(Book book) {
        if (books == null) books = new HashSet<>();
        books.add(book);
        book.setShop(this);
    }
    public void addShopDetail(ShopDetail shopDetail){
        setShopDetail(shopDetail);
        shopDetail.setShop(this);
    }
}
