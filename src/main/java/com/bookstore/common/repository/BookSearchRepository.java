package com.bookstore.common.repository;

import com.bookstore.common.dto.response.PageResponse;
import com.bookstore.common.model.Book;
import com.bookstore.common.model.BookImage;
import com.bookstore.modules.book.dto.BookDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BookSearchRepository {

    @PersistenceContext
    EntityManager entityManager;
    private static final String LIKE_FORMAT = "%%%s%%";
    String SORT_BY = "(\\w+?)(:)(.*)";


    /**
     * Custom Search book using keyword and
     *
     * @param pageNo
     * @param pageSize
     * @param search
     * @param sortBy
     * @return user book with sorting and paging
     */

    public PageResponse getAll(int pageNo, int pageSize, String search, String sortBy) {
        log.info("Execute search user with keyword={}", search);

//        StringBuilder sql = new StringBuilder("SELECT new com.bookstore.modules.book.dto.BookDto(b.id, b.title, b.price, b.author, b.currentQuantity, b.soldQuantity) " +
        StringBuilder sql = new StringBuilder("SELECT b.id, b.title, b.price, b.author, b.currentQuantity, b.soldQuantity " +
                "FROM Book b WHERE 1=1 ");
        if (StringUtils.hasLength(search)) {
//            sql.append(" AND lower(b.title) like lower(:title)");
//            sql.append(" OR lower(b.author) like lower(:author)");
            sql.append(" AND (lower(b.title) like lower(:title) OR lower(b.author) like lower(:author))");

        }
        if (StringUtils.hasLength(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                sql.append(String.format(" ORDER BY b.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        // Get list of users
        Query selectQuery = entityManager.createQuery(sql.toString());
        //set param
        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("title", "%" + search + "%"); //Like ~ %search%
            selectQuery.setParameter("author", String.format(LIKE_FORMAT, search));
        }
        selectQuery.setFirstResult(pageNo); // offset -> position get
        selectQuery.setMaxResults(pageSize); // number of records getted

        List<?> books = selectQuery.getResultList();
        List<BookDto> bookDtoList = new ArrayList<>();

        for (Object book : books) {
            Object[] bookData = (Object[]) book;
            BookDto bookDto = BookDto.builder()
                    .id((Integer) bookData[0])
                    .title((String) bookData[1])
                    .price((Double) bookData[2])
                    .author((String) bookData[3])
                    .currentQuantity((Integer) bookData[4])
                    .soldQuantity((Integer) bookData[5])
                    .imagePath(null)
                    .build();
            bookDtoList.add(bookDto);
        }


        // Count record
//        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM User u");
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) " +
                "FROM Book b");
        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" WHERE lower(b.title) like lower(?1)");
            sqlCountQuery.append(" OR lower(b.author) like lower(?2)");
        }

        Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)) {
            countQuery.setParameter(1, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(2, String.format(LIKE_FORMAT, search));
            countQuery.getSingleResult();
        }

        Long totalElements = (Long) countQuery.getSingleResult();


        log.info("totalElements={}", totalElements);
        Pageable pageable = PageRequest.of(pageNo, pageSize); //đưa sort vào k đc

        Page<?> page = new PageImpl<>(bookDtoList, pageable, totalElements);

        return PageResponse.builder()
                .page(pageNo)
                .size(pageSize)
                .total(page.getTotalPages())
                .items(bookDtoList)
                .build();
    }

}
