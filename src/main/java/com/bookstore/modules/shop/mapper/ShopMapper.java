package com.bookstore.modules.shop.mapper;

import com.bookstore.common.model.Shop;
import com.bookstore.modules.shop.dto.ShopDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopDto toDto(Shop shop);
}
