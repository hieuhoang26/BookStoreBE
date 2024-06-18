package com.bookstore.common.service;

import com.bookstore.common.dto.response.PageResponse;
import com.bookstore.common.model.Book;
import com.bookstore.common.model.BookImage;
import com.bookstore.modules.book.dto.BookDto;
import com.bookstore.modules.book.dto.request.CreateBookRequest;
import com.bookstore.modules.book.dto.response.BookDetailResponse;

import java.util.List;

public interface BookService {
    PageResponse getAllBook(int pageNo, int pageSize, String search, String sortBy);

    Book getBookById(Integer id);
    List<String> getBookImageByBookId(Integer id);

    BookDetailResponse getBookDetailByBookId(Integer id);
    List<BookDto> getAllBookByShopId(Integer id);
    void createBook(Integer shopId, CreateBookRequest request);
    void updateBook(Integer bookId, CreateBookRequest request);
    void deleteBook(Integer bookId);




}
