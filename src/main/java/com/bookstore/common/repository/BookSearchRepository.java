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
//    String SORT_BY = "(\\w+)(?::(\\w+))?";


    /**
     * Custom Search book using keyword and
     *
     * @param pageNo
     * @param pageSize
     * @param search
     * @param sortBy
     * @return user book with sorting and paging
     */
    public PageResponse getAll(Integer pageNo, Integer pageSize, String sortBy, String search, Integer minPrice, Integer maxPrice, Integer category) {
        log.info("Execute search with keyword={}, category={}, minPrice={}, maxPrice={}", search, category, minPrice, maxPrice);

        StringBuilder p1 = new StringBuilder("SELECT b.id, b.title, b.price, b.author, b.currentQuantity, b.soldQuantity ");
        StringBuilder p2 = new StringBuilder("FROM Book b JOIN b.categories c WHERE 1=1 ");

        if (StringUtils.hasLength(search)) {
            p2.append(" AND (lower(b.title) like lower(:title) OR lower(b.author) like lower(:author))");
        }

        if (minPrice != null) {
            p2.append(" AND b.price >= :minPrice");
        }
        if (maxPrice != null) {
            p2.append(" AND b.price <= :maxPrice");
        }
        if (category != null) {
            p2.append(" AND c.id = :categoryId");
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                p2.append(String.format(" ORDER BY b.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        String queryStr = p1.append(p2).toString();
        Query selectQuery = entityManager.createQuery(queryStr);

        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("title", "%" + search + "%");
            selectQuery.setParameter("author", "%" + search + "%");
        }
        if (minPrice != null) {
            selectQuery.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            selectQuery.setParameter("maxPrice", maxPrice);
        }
        if (category != null) {
            selectQuery.setParameter("categoryId", category);
        }

        int firstResult = (pageNo - 1) * pageSize;
        selectQuery.setFirstResult(firstResult);
        selectQuery.setMaxResults(pageSize);

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

        // Count records
        StringBuilder countQueryStr = new StringBuilder("SELECT COUNT(b) ");
        StringBuilder countQueryCondition = new StringBuilder("FROM Book b JOIN b.categories c WHERE 1=1 ");

        if (StringUtils.hasLength(search)) {
            countQueryCondition.append(" AND (lower(b.title) like lower(:title) OR lower(b.author) like lower(:author))");
        }
        if (minPrice != null) {
            countQueryCondition.append(" AND b.price >= :minPrice");
        }
        if (maxPrice != null) {
            countQueryCondition.append(" AND b.price <= :maxPrice");
        }
        if (category != null) {
            countQueryCondition.append(" AND c.id = :categoryId");
        }

        countQueryStr.append(countQueryCondition);
        Query countQuery = entityManager.createQuery(countQueryStr.toString());

        if (StringUtils.hasLength(search)) {
            countQuery.setParameter("title", "%" + search + "%");
            countQuery.setParameter("author", "%" + search + "%");
        }
        if (minPrice != null) {
            countQuery.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            countQuery.setParameter("maxPrice", maxPrice);
        }
        if (category != null) {
            countQuery.setParameter("categoryId", category);
        }

        Long totalElements = (Long) countQuery.getSingleResult();

        log.info("totalElements={}", totalElements);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<?> page = new PageImpl<>(bookDtoList, pageable, totalElements);
        return PageResponse.builder()
                .page(pageNo)
                .size(pageSize)
                .total(page.getTotalPages())
                .items(bookDtoList)
                .build();
    }


}
