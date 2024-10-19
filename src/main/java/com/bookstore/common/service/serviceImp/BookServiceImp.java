package com.bookstore.common.service.serviceImp;

import com.bookstore.common.dto.response.PageResponse;
import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.model.*;
import com.bookstore.common.repository.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
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
    private final OrderRepository orderRepository;

    @Override
    public PageResponse getAllBook(Integer pageNo, Integer pageSize, String sortBy, String search, Integer minPrice, Integer maxPrice, Integer category) {
        return bookSearchRepository.getAll(pageNo, pageSize, sortBy,search,minPrice , maxPrice, category);
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
        Book book = Book.builder()
                .title(request.getTitle())
                .price(request.getPrice())
                .author(request.getAuthor())
                .soldQuantity(request.getSoldQuantity())
                .currentQuantity(request.getCurrentQuantity())
                .build();
        if(request.getCategory()!=null){
            List<Category> categories = request.getCategory().stream().map(categoryService::getById).toList();
            categories.forEach(book::addCategory);
        }
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
            if((request.getImages() != null && !request.getImages().isEmpty())){
                request.getImages().forEach(image -> {
                    String fileName = "image_" + image.getOriginalFilename();

                    if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
                        fileName += ".jpg";
                    }
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
            }
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
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        // Update basic book details
        book.setTitle(request.getTitle());
        book.setPrice(request.getPrice());
        book.setAuthor(request.getAuthor());
        book.setCurrentQuantity(request.getCurrentQuantity());
        book.setSoldQuantity(request.getSoldQuantity());

        // Update categories if present
        if (request.getCategory() != null) {
            List<Category> categories = request.getCategory().stream()
                    .map(categoryService::getById).toList();
            bookRepository.deleteBookCategory(bookId);
            book.setCategories(new HashSet<>());
            categories.forEach(book::addCategory);
        }

        // Update book details
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

        // Image handling
        String imageDir = "D:/BookStore/TestImg/";
        List<BookImage> existingImages = new ArrayList<>(book.getBookImages());
        List<String> savedImagePaths = new ArrayList<>();

        try {
            // Upload new images
            if (request.getImages() != null && !request.getImages().isEmpty()) {
                request.getImages().forEach(image -> {
                    boolean imageExists = existingImages.stream()
                            .anyMatch(bookImage -> bookImage.getImagePath().contains(image.getOriginalFilename()));

                    if (!imageExists) {
                        String fileName = "image_" + image.getOriginalFilename();
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
                    }
                });
            }

            // Delete old images that are not part of the current request
            List<String> requestImageNames = request.getImages().stream()
                    .map(MultipartFile::getOriginalFilename)
//                    .map(image -> "image_" + image.getOriginalFilename())
                    .toList();

            List<BookImage> imagesToRemove = existingImages.stream()
                    .filter(image -> !requestImageNames.contains(image.getImagePath()))
                    .toList();

            imagesToRemove.forEach(image -> {
                System.out.println("need remove: "+  image);
                File oldFile = new File(imageDir + image.getImagePath());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
                book.getBookImages().remove(image);
                bookImageService.deleteBookImageById(image.getId());
            });

            // Save the updated book to the repository
            bookRepository.save(book);

        } catch (Exception e) {
            // Rollback image files in case of error
            savedImagePaths.forEach(path -> {
                try {
                    Files.deleteIfExists(new File(path).toPath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            throw new RuntimeException("Error updating book", e);
        }
    }



    @Override
    public void deleteBook(Integer bookId) {
        Book book = bookRepository.findById(Long.valueOf(bookId)).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        String imageDir = "D:/BookStore/TestImg/";

        List<BookImage> bookImages = new ArrayList<>(book.getBookImages());

        try {
            bookImages.forEach(image -> {
                File file = new File(imageDir + image.getImagePath());
                if (file.exists()) {
                    if (!file.delete()) {
                        throw new RuntimeException("Failed to delete image: " + file.getPath());
                    }
                }
                bookImageService.deleteBookImage(image.getId());
            });
            bookRepository.deleteById(Long.valueOf(bookId));

        } catch (Exception e) {
            throw new RuntimeException("Error deleting book and associated images", e);
        }
    }



}
