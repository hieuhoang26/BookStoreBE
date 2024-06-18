package com.bookstore.common.repository;

import com.bookstore.common.model.ShopDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDetailRepository extends JpaRepository<ShopDetail,Long> {
}
