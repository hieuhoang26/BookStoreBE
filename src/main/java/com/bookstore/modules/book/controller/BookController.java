package com.bookstore.modules.book.controller;

import com.bookstore.common.dto.response.PageResponse;
import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.model.Book;
import com.bookstore.common.model.Category;
import com.bookstore.common.model.Shop;
import com.bookstore.common.service.BookService;
import com.bookstore.common.service.ShopService;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.book.dto.BookDto;
import com.bookstore.modules.book.dto.RateDto;
import com.bookstore.modules.book.dto.request.CreateBookRequest;
import com.bookstore.modules.book.dto.response.BookDetailResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ShopService shopService;

    @GetMapping(value = Uri.BOOK_PAGE)
    public ResponseData<?> GetAllBook(@RequestParam(defaultValue = "1", required = false) Integer pageNo,
                                      @RequestParam(defaultValue = "2", required = false) Integer pageSize,
                                      @RequestParam(required = false) String sortBy,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) Integer minPrice,
                                      @RequestParam(required = false) Integer maxPrice,
                                      @RequestParam(required = false) Integer category
    ) {
        log.info("Request get list of book and search with paging and sorting");
        PageResponse<BookDto> rs = bookService.getAllBook(pageNo, pageSize, sortBy,search,minPrice , maxPrice, category);
        List<BookDto> bookDtos = (List<BookDto>) rs.getItems();
        bookDtos.forEach(bookDto -> {
            List<String> image = bookService.getBookImageByBookId(bookDto.getId());
            bookDto.setImagePath(image);
        });
        return new ResponseData<>(HttpStatus.OK.value(), "users", rs);


    }

    @GetMapping(value = Uri.BOOK + "/{id}")
    public ResponseData GetBookById(@PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        BookDto rs = BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .price(book.getPrice())
                .author(book.getAuthor())
                .currentQuantity(book.getCurrentQuantity())
                .soldQuantity(book.getSoldQuantity())
                .build();
        List<String> image = bookService.getBookImageByBookId(id);
        rs.setImagePath(image);
        return new ResponseData(HttpStatus.OK.value(), "Book By Id", rs);
    }

    @GetMapping(value = Uri.BOOK_DETAIL)
    public ResponseData GetBookDetailById(@PathVariable Integer id) {
        BookDetailResponse res = bookService.getBookDetailByBookId(id);
        return new ResponseData(HttpStatus.OK.value(), "BookDetail By Id", res);

    }

    @GetMapping(value = Uri.BOOK_SHOP)
    public ResponseData GetAllBookForShopId(@PathVariable Integer id) {
        List<BookDto> res = bookService.getAllBookByShopId(id);
        return new ResponseData(HttpStatus.OK.value(), "All book for shop", res);

    }

    @PostMapping(value = Uri.BOOK)
//    public ResponseData CreateBook(@RequestParam Integer shopId, @Valid @ModelAttribute CreateBookRequest request) {
    public ResponseData CreateBook(@RequestParam Integer shopId, @ModelAttribute CreateBookRequest request) {
        bookService.createBook(shopId, request);
        return new ResponseData(HttpStatus.CREATED.value(), "Create Book Success");
    }

    @PutMapping(value = Uri.BOOK)
    public ResponseData UpdateBook(@RequestParam Integer bookId, @Valid @ModelAttribute CreateBookRequest request) {
        bookService.updateBook(bookId, request);
        return new ResponseData(HttpStatus.OK.value(), "Update Book Success");
    }
    @DeleteMapping(value = Uri.BOOK)
    public ResponseData DeleteBook(@RequestParam Integer bookId) {
        bookService.deleteBook(bookId);
        return new ResponseData(HttpStatus.OK.value(), "Delete Book Success");
    }

    /* <----------------------- Uri.BOOKS_REVIEWS -------------------> */

    /* <----------------------- Uri.BOOKS_RATES ---------------------> */


}
