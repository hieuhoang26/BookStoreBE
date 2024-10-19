package com.bookstore.modules.shop.controller;

import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.model.Shop;
import com.bookstore.common.service.ShopService;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.order.dto.OrderDto;
import com.bookstore.modules.shop.dto.ShopDetailsDto;
import com.bookstore.modules.shop.dto.ShopDto;
import com.bookstore.modules.shop.dto.request.ShopRequest;
import com.bookstore.modules.shop.mapper.ShopMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ShopController {
    final ShopService shopService;
    final ShopMapper shopMapper;

    @GetMapping(value = {Uri.SHOP + "/{shopId}"})
    public ResponseData<?> RetrieveShopById(@PathVariable Integer shopId){
        Shop shop = shopService.getShopById(shopId);
        return new ResponseData<ShopDto>(HttpStatus.OK.value(),"Get Shop By Id",  shopMapper.toDto(shop));
    }
    @GetMapping(value = {Uri.SHOP })
    public ResponseData<?> RetrieveShopByUserId(@RequestParam Integer userId){
        Shop shop = shopService.getShopByUserId(userId);
        return new ResponseData<>(HttpStatus.OK.value(),"Get Shop By userId", shopMapper.toDto(shop));
    }
    @GetMapping(value = {Uri.SHOP_BOOK })
    public ResponseData<?> RetrieveShopByBookId(@RequestParam Integer bookId){
        Shop shop = shopService.getShopByBookId(bookId);
        return new ResponseData<>(HttpStatus.OK.value(),"Get Shop By bookId", shopMapper.toDto(shop));
    }
    @PostMapping(value = {Uri.SHOP })
    public ResponseData<?> CreateShopForUser(@Valid @ModelAttribute ShopRequest shopRequest){
        shopService.createShop(shopRequest);
        return new ResponseData<>(HttpStatus.CREATED.value(),"Create Shop Success");
    }
    @PutMapping(value = {Uri.SHOP })
    public ResponseData<?> UpdateShopForUser(@Valid @ModelAttribute ShopRequest shopRequest){
        shopService.updateShop(shopRequest);
        return new ResponseData<>(HttpStatus.OK.value(),"Update Shop Success");
    }
    @DeleteMapping(value = {Uri.SHOP })
    public ResponseData<?> DeleteShopForUser(@RequestParam Integer userId){
        shopService.deleteShop(userId);
        return new ResponseData<>(HttpStatus.OK.value(),"Delete Shop Success");
    }
    @GetMapping(value = {Uri.SHOP_DETAIL})
    public ResponseData<?> GetShopDetailForShop(@RequestParam Integer shopId){
        return new ResponseData<>( HttpStatus.OK.value(),"ShopDetail for shop",shopService.getShopDetailForShop(shopId));
    }
    @PutMapping(value = {Uri.SHOP_DETAIL})
    public ResponseData<?> UpdateShopDetailForShop(@RequestParam Integer shopId, @RequestBody ShopDetailsDto request){
        shopService.updateShopDetail(shopId,request);
        return new ResponseData<>( HttpStatus.OK.value(),"Update ShopDetail for shop");
    }


}
