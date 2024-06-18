package com.bookstore.common.repository;

import com.bookstore.common.dto.response.PageResponse;
import com.bookstore.common.model.Book;
import com.bookstore.common.model.BookDetail;
import com.bookstore.common.model.BookImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @PersistenceContext
    EntityManager entityManager = null;

    @Query("select bimg from BookImage bimg where bimg.book.Id = :id")
    List<BookImage> findBookImageByBookId(Integer id);
    @Query("select b from BookDetail b where b.book.Id = :id")
    BookDetail findBookDetailsByBookId(Integer id);

    List<Book> findBookByShopId(Integer Id);

    @Modifying
    @Transactional
    @Query(value = "delete from book_category bc where bc.book_id = :bookId", nativeQuery = true)
    void deleteBookCategory(Integer bookId);


}
