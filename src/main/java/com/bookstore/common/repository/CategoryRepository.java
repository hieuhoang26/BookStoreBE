package com.bookstore.common.repository;

import com.bookstore.common.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select c from Category c where c.parentId = 1")
    List<Category> findAllParentCategory();
    List<Category> findCategoriesByParentId(Integer parentId);
}
