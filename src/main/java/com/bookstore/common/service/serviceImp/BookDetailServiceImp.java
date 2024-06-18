package com.bookstore.common.service.serviceImp;

import com.bookstore.common.model.BookDetail;
import com.bookstore.common.repository.BookDetailRepository;
import com.bookstore.common.service.BookDetailService;
import com.bookstore.modules.book.dto.response.BookDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookDetailServiceImp implements BookDetailService {
    final BookDetailRepository bookDetailRepository;
    @Override
    public void save(BookDetail bookDetail) {
        bookDetailRepository.save(bookDetail);
    }
}
