package com.bookstore.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name = "category")
public class Category extends  Common<Integer>{
    @NotBlank
    @Column(name = "CATEGORY_NAME")
    String categoryName;

    @Column(name = "parent_id")
    Integer parentId;


    /* <------------ Mapping -------------> */
    /* To BookCategory */
    /* Delete Category, Delete Book, Update Later */
    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    Set<Book> books = new HashSet<>();
    /* <------------ Entity Method -------------> */

    public Category(String name, Integer parentId) {
        this.categoryName = name;
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "Id = " + getId()+
                ",name='" + categoryName + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
