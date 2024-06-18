package com.bookstore.common.service.serviceImp;

import com.bookstore.common.dto.response.PageResponse;
import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.model.*;
import com.bookstore.common.repository.BookRepository;
import com.bookstore.common.repository.BookSearchRepository;
import com.bookstore.common.repository.CategoryRepository;
import com.bookstore.common.repository.ShopRepository;
import com.bookstore.common.service.*;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.book.dto.BookDto;
import com.bookstore.modules.book.dto.request.CreateBookRequest;
import com.bookstore.modules.book.dto.response.BookDetailResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;
    private final ShopService shopService;
    private final CategoryService categoryService;
    private final BookDetailService bookDetailService;
    private final BookImageService bookImageService;
    private final BookSearchRepository bookSearchRepository;

    @Override
    public PageResponse getAllBook(int pageNo, int pageSize, String search, String sortBy) {
        return bookSearchRepository.getAll(pageNo,pageSize,search,sortBy);
    }

    @Override
    public Book getBookById(Integer id) {
        Book book = bookRepository.findById(Long.valueOf(id)).orElse(null);
        return book;
    }

    @Override
    public List<String> getBookImageByBookId(Integer id) {
        List<BookImage> bookImage = bookRepository.findBookImageByBookId(id);
        return bookImage.stream()
                .map(BookImage::getImagePath)
                .collect(Collectors.toList());
    }

    @Override
    public BookDetailResponse getBookDetailByBookId(Integer id) {
        Book book = bookRepository.findById(Long.valueOf(id)).orElse(null);
        Shop shop = shopService.getShopByBookId(id);
        BookDetail bookDetail = bookRepository.findBookDetailsByBookId(id);

        BookDetailResponse rs = BookDetailResponse.builder()
                .id(book.getId())
                .shopId(shop.getId())
                .shopName(shop.getShopName())
                .title(book.getTitle())
                .price(book.getPrice())
                .author(book.getAuthor())
                .currentQuantity(book.getCurrentQuantity())
                .soldQuantity(book.getSoldQuantity())

                .publisher(bookDetail.getPublisher())
                .publicationDate(bookDetail.getPublicationDate())
                .dimension(bookDetail.getDimensions())
                .coverType(bookDetail.getCoverType())
                .numberOfPages(bookDetail.getNumberPages())
                .description(bookDetail.getDescription())

                .categories(book.getCategories().stream()
                        .map(Category::getCategoryName)
                        .collect(Collectors.toList())
                )
                .images(book.getBookImages().stream()
                        .map(BookImage::getImagePath)
                        .collect(Collectors.toList())
                )
                .build();
        return rs;
    }

    @Override
    public List<BookDto> getAllBookByShopId(Integer id) {
        List<Book> books = bookRepository.findBookByShopId(id);
        return books.stream().map(book -> {
                    List<BookImage> bookImage = bookRepository.findBookImageByBookId(Math.toIntExact(Long.valueOf(book.getId())));
                    List<String> imagePath = bookImage.stream().map(BookImage::getImagePath).collect(Collectors.toList());
                    return BookDto.builder()
                            .id(book.getId())
                            .title(book.getTitle())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .author(book.getAuthor())
                            .currentQuantity(book.getCurrentQuantity())
                            .soldQuantity(book.getSoldQuantity())
                            .imagePath(imagePath)
                            .build();
                }
        ).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createBook(Integer shopId, CreateBookRequest request) {
        Shop shop = shopService.getShopById(shopId);
        List<Category> categories = request.getCategory().stream().map(categoryService::getById).toList();
        Book book = Book.builder()
                .title(request.getTitle())
                .price(request.getPrice())
                .author(request.getAuthor())
                .soldQuantity(0)
                .currentQuantity(request.getCurrentQuantity())
                .build();

        categories.forEach(book::addCategory);

        book.setBookDetail(
                BookDetail.builder()
                        .publisher(request.getPublisher())
                        .publicationDate(request.getPublicationDate())
                        .dimensions(request.getDimension())
                        .coverType(request.getCoverType())
                        .numberPages(request.getNumberOfPages())
                        .description(request.getDescription())
                        .build()
        );
        List<String> savedImagePaths = new ArrayList<>();
        String imageDir = "D:/BookStore/TestImg/";

        try {
            request.getImages().forEach(image -> {
                String fileName = "image_" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
                String imagePath = imageDir + fileName;
                try {
                    image.transferTo(new File(imagePath));
                    savedImagePaths.add(imagePath);
                    book.addBookImage(
                            BookImage.builder()
                                    .imagePath(fileName)
                                    .build()
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            bookRepository.save(book);
            shop.addBook(book);
            shopService.save(shop);

        } catch (Exception e) {
            savedImagePaths.forEach(path -> {
                try {
                    Files.deleteIfExists(new File(path).toPath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            throw e;
        }
    }

    @Transactional
    @Override
    public void updateBook(Integer bookId, CreateBookRequest request) {
        Book book = bookRepository.findById(Long.valueOf(bookId)).orElse(null);
        book.setTitle(request.getTitle());
        book.setPrice(request.getPrice());
        book.setAuthor(request.getAuthor());
        book.setCurrentQuantity(request.getCurrentQuantity());
        book.setSoldQuantity(request.getSoldQuantity());

        if(request.getCategory()!=null){
            List<Category> categories = request.getCategory().stream().map(categoryService::getById).toList();
            bookRepository.deleteBookCategory(bookId);
            book.setCategories(new HashSet<>());
            categories.forEach(book::addCategory);
        }
        BookDetail bookDetail = book.getBookDetail();
        if (bookDetail == null) {
            bookDetail = new BookDetail();
            book.setBookDetail(bookDetail);
        }
        bookDetail.setPublisher(request.getPublisher());
        bookDetail.setPublicationDate(request.getPublicationDate());
        bookDetail.setDimensions(request.getDimension());
        bookDetail.setCoverType(request.getCoverType());
        bookDetail.setNumberPages(request.getNumberOfPages());
        bookDetail.setDescription(request.getDescription());

        String imageDir = "D:/BookStore/TestImg/";
        // delete old image
        List<BookImage> oldImages = book.getBookImages().stream().toList();
        bookImageService.deleteBookImage(bookId);
        oldImages.forEach(image -> {
            File oldFile = new File(imageDir + image.getImagePath());
            if (oldFile.exists()) {
                oldFile.delete();
            }
        });
        List<String> newImagePaths = new ArrayList<>();
        try {
            request.getImages().forEach(image -> {
                String fileName = "image_" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
                String imagePath = imageDir + fileName;
                try {
                    image.transferTo(new File(imagePath));
                    newImagePaths.add(imagePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            book.getBookImages().clear();
            newImagePaths.forEach(imagePath -> book.addBookImage(
                    BookImage.builder()
                            .imagePath(imagePath.substring(imageDir.length()))
                            .build()
            ));

            bookRepository.save(book);

        } catch (Exception e) {
            newImagePaths.forEach(path -> {
                try {
                    Files.deleteIfExists(new File(path).toPath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            throw e;
        }

    }

    @Override
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(Long.valueOf(bookId));
    }


}
