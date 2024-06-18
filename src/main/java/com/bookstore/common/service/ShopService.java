package com.bookstore.common.service;

import com.bookstore.common.model.Shop;
import com.bookstore.common.model.ShopDetail;
import com.bookstore.modules.shop.dto.ShopDetailsDto;
import com.bookstore.modules.shop.dto.request.ShopRequest;

public interface ShopService {
    void save(Shop shop);
    Shop getShopByBookId(Integer bookId);
    Shop getShopById(Integer id);
    Shop getShopByUserId(Integer id);
    void createShop(ShopRequest request);
    void updateShop(ShopRequest request);
    void deleteShop(Integer userId);
    ShopDetailsDto getShopDetailForShop(Integer shopId);
    void updateShopDetail(Integer shopId, ShopDetailsDto request);
}
