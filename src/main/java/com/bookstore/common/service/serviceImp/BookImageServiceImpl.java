package com.bookstore.common.service.serviceImp;

import com.bookstore.common.model.BookImage;
import com.bookstore.common.repository.BookImageRepository;
import com.bookstore.common.service.BookImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookImageServiceImpl implements BookImageService {
    final BookImageRepository bookImageRepository;
    @Override
    public void deleteBookImage(Integer bookId) {
        bookImageRepository.deleteBookImageByBookId(bookId);
    }
}
