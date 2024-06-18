package com.bookstore.common.service.serviceImp;

import com.bookstore.common.model.ShopDetail;
import com.bookstore.common.repository.ShopDetailRepository;
import com.bookstore.common.service.ShopDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopDetailServiceImp implements ShopDetailService {
    final ShopDetailRepository shopDetailRepository;
    @Override
    public void save(ShopDetail shopDetail) {
        shopDetailRepository.save(shopDetail);
    }
}
