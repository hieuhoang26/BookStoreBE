package com.bookstore.common.model;

import com.bookstore.common.model.Auth.GroupHasUser;
import com.bookstore.common.model.Auth.Role;
import com.bookstore.common.model.Auth.UserHasRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "USER")
public class User extends  Common<Integer> implements UserDetails, Serializable {
    @Size(min = 5, max = 30)
    @Column(name = "USER_NAME", unique = true)
    private String username;

    @Size(min = 3)
    @Column(name = "PASSWORD")
    private String password;

    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    //    @Column(name = "type")
//    String type;
//
//    @Column(name = "social_id")
//    String socialId;

    /*-----------Mapping--------------------*/

    // (n-n) Role
    // Delete user, not delete role
//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(name = "USER_ROLE",
//            joinColumns = {@JoinColumn(referencedColumnName = "ID")},   //userId
//            inverseJoinColumns = {@JoinColumn(referencedColumnName = "ID")})    //roleID
//    private Set<Role> roles;

    //   (1-n) Order
    /* Delete User, Delete Order */
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user")
    private Set<Order> orders;

    //    (1-1) shop
    /* Delete User, Delete Shop */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SHOP_ID", referencedColumnName = "ID")
    private Shop shop;

    /* 1-n Book */
    /* Delete User, Delete All Book Belong To User */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    Set<Book> books;

    /* 1-n Review */
    /* Delete User, Delete Review */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    Set<Review> reviews;

    /* 1-n Rate */
    /* Delete User, Delete Rate */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    Set<Rate> rates;

    /* Auth */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserHasRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<GroupHasUser> groups = new HashSet<>();



    /*-----------Convert Method--------------------*/
    public void addRole(Role role) {
        UserHasRole userHasRole = new UserHasRole();
        userHasRole.setUser(this);
        userHasRole.setRole(role);
        roles.add(userHasRole);
    }

    public void removeRole(Role role) {
        roles.removeIf(userHasRole -> userHasRole.getRole().equals(role));
    }
    public void addBook(Book book){
        if(books == null) books = new HashSet<>();
        books.add(book);
        book.setUser(this);
    }
    public void addOrder(Order order){
        if(orders == null) orders = new HashSet<>();
        orders.add(order);
        order.setUser(this);
    }
    public void addShop(Shop shop){
        setShop(shop);
        shop.setUser(this);
    }
    public void addReview(Review review){
        if(reviews == null) reviews = new HashSet<>();
        reviews.add(review);
    }
    /*-----------Method--------------------*/
    public User(String userName, String password, String email, String phoneNumber) {
        this.username = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserHasRole userHasRole : roles) {
            authorities.add(new SimpleGrantedAuthority(userHasRole.getRole().getName()));
        }
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // status user

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
