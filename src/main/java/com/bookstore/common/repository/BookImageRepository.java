package com.bookstore.common.repository;

import com.bookstore.common.model.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookImageRepository extends JpaRepository<BookImage,Long> {
    void deleteBookImageByBookId(Integer id);
}
