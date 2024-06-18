package com.bookstore.common.repository;

import com.bookstore.common.model.Shop;
import com.bookstore.common.model.ShopDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    @Query("from Shop s join fetch s.books b where b.Id = :bookId")
    Shop findShopByBookId(Integer bookId);
    Shop findShopByUserId(Integer id);
    @Query("select d, s from Shop s join fetch s.shopDetail d where s.Id = :id")
    ShopDetail findShopDetailsByShopId(Integer id);
}
